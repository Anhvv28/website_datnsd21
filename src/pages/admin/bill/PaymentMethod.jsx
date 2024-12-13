import React, { useEffect, useState, useCallback } from "react";
import {
  Button,
  Col,
  Form,
  Input,
  InputNumber,
  Modal,
  Row,
  Table,
  Tag,
} from "antd";
import TextArea from "antd/es/input/TextArea";
import Title from "antd/es/typography/Title";
import { toast } from "react-toastify";
import FormatCurrency from "~/utils/FormatCurrency";
import FormatDate from "~/utils/FormatDate";
import formatCurrency from "~/utils/format";
import * as request from "~/utils/httpRequest";

const PAYMENT_METHOD = {
  CASH: 0,
  TRANSFER: 1,
};

function PaymentMethod({ bill, onSucess }) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState([]);
  const [method, setMethod] = useState(PAYMENT_METHOD.CASH);

  const [totalPayment, setTotalPayment] = useState(0);
  const [totalPaymentRefund, setTotalPaymentRefund] = useState(0);
  const [extraMoney, setExtraMoney] = useState(null);

  const [totalBillDetail, setTotalBillDetail] = useState(0);
  const [totalBillDetailRefund, setTotalBillDetailRefund] = useState(0);

  const [isRefund, setIsRefund] = useState(false);
  const [form] = Form.useForm();

  const calculateTotals = useCallback((paymentData, billDetailData) => {
    const totalPaymentReceived = paymentData
      .filter((item) => item.type === true) // type === true means payment
      .reduce((sum, item) => sum + item.totalMoney, 0);

    const totalPaymentReturn = paymentData
      .filter((item) => item.type === false) // type === false means refund
      .reduce((sum, item) => sum + item.totalMoney, 0);

      const totalBill = billDetailData.reduce(
        (sum, item) => sum + item.quantity * (item.discountValue || item.price),
        0
      );
      const moneyToPay = bill.totalMoney + bill.moneyShip - totalPayment - bill.moneyReduce;
      form.setFieldsValue({
        totalMoney: moneyToPay,
      });
      

    const totalBillRefunded = billDetailData
      .filter((item) => item.status === true)
      .reduce(
        (sum, item) =>
          sum +
          item.quantity *
            (item.discountPercent !== null ? item.discountValue : item.price),
        0
      );

    setTotalPayment(totalPaymentReceived);
    setTotalPaymentRefund(totalPaymentReturn);
    setTotalBillDetail(totalBill);
    setTotalBillDetailRefund(totalBillRefunded);

    // Set default form value for totalMoney after all totals are calculated
    let defaultTotalMoney;
    const moneyToReturn = totalBillRefunded - totalPaymentReturn - bill.moneyReduce;
    if (isRefund) {
      // If refunding, set the form's totalMoney to the amount we need to return to the customer
      defaultTotalMoney = moneyToReturn;
    } else {
      // If paying, set the form's totalMoney to the remaining amount due
      // If there's no refund scenario:
      // total needed = bill total + ship - totalPayment
      // If refund scenario handled above, this scenario is straightforward
      defaultTotalMoney = bill.totalMoney + bill.moneyShip - totalPayment;
    }

    form.setFieldsValue({
      totalMoney: defaultTotalMoney,
    });
  }, [bill, form, isRefund, totalPayment, totalPaymentRefund, totalBillDetailRefund]);

  const loadPaymentData = useCallback(async () => {
    try {
        // Fetch payment methods
        const paymentData = await request.get(`/payment-method/${bill.id}`);

        // Fetch bill details
        const billDetailResponse = await request.get(`/bill-detail`, {
            params: { bill: bill.id, page: 1, sizePage: 1_000_000 },
        });

        const billDetailData = billDetailResponse.data;

        // Calculate totals
        calculateTotals(paymentData, billDetailData);

        setPaymentMethod(paymentData); // Update payment methods in the state
    } catch (error) {
        console.error(error);
    }
}, [bill.id, calculateTotals]);


  useEffect(() => {
    loadPaymentData();
  }, [bill, loadPaymentData]);

  const handleCreatePaymentMethod = async (data) => {
    try {
        data.type = !isRefund;
        data.method = method;
        data.bill = bill.id;
        data.discountValue = bill.discountValue; // Include the discount amount or percentage
        data.discountPercent = bill.discountPercent; // If applicable

        await request.post(`/payment-method`, data); // Ensure backend handles and persists discounts
        await loadPaymentData(); // Refetch bill details after payment
        onSucess();
        toast.success(`Đã thanh toán ${formatCurrency(data.totalMoney)}`);
        setIsModalOpen(false);
        form.resetFields();
    } catch (error) {
        console.error(error);
        toast.error(error?.response?.data || "Có lỗi xảy ra!");
    }
};


  const columns = [
    {
      title: "#",
      dataIndex: "index",
      key: "index",
    },
    {
      title: "Số tiền",
      dataIndex: "totalMoney",
      key: "totalMoney",
      render: (x) => <FormatCurrency value={x} />,
    },
    {
      title: "Thời gian",
      dataIndex: "createAt",
      key: "createAt",
      render: (x) => <FormatDate date={x} />,
    },
    {
      title: "Mã giao dịch",
      dataIndex: "tradingCode",
      key: "tradingCode",
      render: (x) => (x === null ? "---" : x),
    },
    {
      title: "Loại giao dịch",
      dataIndex: "type",
      key: "type",
      render: (x) => (
        <Tag
          color={x === true ? "green" : "red"}
          style={{ width: "100px" }}
          className="text-center"
        >
          {x === true ? "Thanh toán" : "Hoàn trả"}
        </Tag>
      ),
    },
    {
      title: "Nhân viên xác nhận",
      dataIndex: "createBy",
      key: "createBy",
    },
    {
      title: "Ghi chú",
      dataIndex: "note",
      key: "note",
    },
  ];

  // Handle changes in the InputNumber for totalMoney (for extraMoney calculation)
  const handleTotalMoneyChange = (value) => {
    if (isRefund) return; // If refunding, extraMoney doesn't apply
    const amountOwed = bill.totalMoney + bill.moneyShip - totalPayment;
    setExtraMoney(value - amountOwed);
  };

  return (
    <>
      <div className="mt-3">
        <div className="d-flex align-items-center">
          <Title level={5} className="text-danger text-uppercase p-0 m-0 flex-grow-1 p-2">
            Lịch sử thanh toán
          </Title>
          <div className="p-2">
            {/* Show payment button only if totalPayment < total needed */}
            {totalPayment < bill.totalMoney + bill.moneyShip && (
              <Button
                type="primary"
                className="text-dark bg-blue"
                onClick={() => {
                  setIsRefund(false);
                  setMethod(PAYMENT_METHOD.CASH);
                  setIsModalOpen(true);
                }}
              >
                Xác nhận thanh toán
              </Button>
            )}

            {/* Show refund button if refund totals are not balanced */}
            {totalBillDetailRefund - (bill.status === 8 ? bill.moneyReduce : 0) !==
              totalPaymentRefund && (
              <Button
                type="primary"
                danger
                onClick={() => {
                  setIsRefund(true);
                  setMethod(PAYMENT_METHOD.CASH);
                  setIsModalOpen(true);
                }}
              >
                Hoàn tiền
              </Button>
            )}
          </div>
        </div>

        <Modal
          title={`Xác nhận ${isRefund ? "hoàn tiền" : "thanh toán"}`}
          open={isModalOpen}
          onOk={() => {
            setIsModalOpen(false);
            setIsRefund(false);
          }}
          onCancel={() => {
            setIsModalOpen(false);
            setIsRefund(false);
          }}
          footer={
            <>
              <Button
                onClick={() => {
                  setIsModalOpen(false);
                  setIsRefund(false);
                }}
              >
                Hủy
              </Button>
              <Button onClick={() => form.submit()} type="primary">
                {isRefund ? "Hoàn tiền" : "Thanh toán"}
              </Button>
            </>
          }
        >
          <Form layout="vertical" form={form} onFinish={handleCreatePaymentMethod}>
            {method === PAYMENT_METHOD.CASH ? (
              <Form.Item
                label={`Tiền ${isRefund ? "trả khách" : "khách đưa"}`}
                name="totalMoney"
                rules={[
                  {
                    required: true,
                    message: `Tiền ${
                      isRefund ? "trả khách" : "khách đưa"
                    } không được để trống!`,
                  },
                ]}
              >
                <InputNumber
                  className="w-100 mb-2"
                  formatter={(value) =>
                    ` ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ",")
                  }
                  suffix="VNĐ"
                  placeholder={`Nhập tiền ${isRefund ? "trả khách..." : "khách đưa..."}`}
                  onChange={handleTotalMoneyChange}
                />
              </Form.Item>
            ) : (
              <Form.Item
                label="Mã giao dịch"
                name="tradingCode"
                rules={[
                  {
                    required: true,
                    message: "Mã giao dịch không được để trống!",
                  },
                ]}
              >
                <Input />
              </Form.Item>
            )}

            <Form.Item
              label="Ghi chú"
              name="note"
              rules={[
                {
                  required: true,
                  message: "Ghi chú không được để trống!",
                },
              ]}
            >
              <TextArea />
            </Form.Item>

            {!isRefund && (
              <Row gutter={10} className="mt-3">
                <Col xl={12} onClick={() => setMethod(PAYMENT_METHOD.CASH)}>
                  <div
                    className={`py-2 border border-2 rounded-2 d-flex align-items-center justify-content-center ${
                      method === PAYMENT_METHOD.TRANSFER
                        ? `text-secondary border-secondary`
                        : "border-warning text-warning"
                    }`}
                  >
                    <i
                      className="fa-solid fa-coins"
                      style={{ fontSize: "36px" }}
                    ></i>
                    <span className="ms-2 fw-semibold text-dark">Tiền mặt</span>
                  </div>
                </Col>
                <Col xl={12} onClick={() => setMethod(PAYMENT_METHOD.TRANSFER)}>
                  <div
                    className={`py-2 border border-2 rounded-2 d-flex align-items-center justify-content-center ${
                      method === PAYMENT_METHOD.CASH
                        ? `text-secondary border-secondary`
                        : "border-warning text-warning"
                    }`}
                  >
                    <i
                      className="fa-regular fa-credit-card"
                      style={{ fontSize: "36px" }}
                    ></i>
                    <span className="ms-2 fw-semibold text-dark">Chuyển khoản</span>
                  </div>
                </Col>
              </Row>
            )}
          </Form>

          {isRefund ? (
            <>
              Cần phải trả lại khách:{" "}
              <span className="float-end fw-semibold text-danger">
                <FormatCurrency
                  value={totalBillDetailRefund - totalPaymentRefund - bill.moneyReduce}
                />
              </span>
            </>
          ) : (
            <div className="mt-3 fw-semibold ">
              Số tiền cần thanh toán:{" "}
              <span className="float-end fw-semibold text-danger">
                <FormatCurrency value={bill.totalMoney + bill.moneyShip - totalPayment} />
              </span>
              <br />
              Tiền thừa trả khách:{" "}
              <span className="float-end text-success">
                <FormatCurrency value={extraMoney < 0 ? 0 : extraMoney} />
              </span>
            </div>
          )}
        </Modal>

        <Table columns={columns} pagination={false} dataSource={paymentMethod} />
      </div>
    </>
  );
}

export default PaymentMethod;
