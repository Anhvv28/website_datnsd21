import React, { useState, useEffect } from "react";
import { Badge, Button, DatePicker, Input, Table, Tooltip, Card, Row, Col } from "antd";
import { Link } from "react-router-dom";
import * as request from "~/utils/httpRequest";
import FormatCurrency from "~/utils/FormatCurrency";
import FormatDate from "~/utils/FormatDate";

const { RangePicker } = DatePicker;

const ReturnProduct = () => {
  const [listOrder, setListOrder] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [searchValue, setSearchValue] = useState("");
  const [pageSize, setPageSize] = useState(5);
  const [selectedDates, setSelectedDates] = useState(null);
  const [summary, setSummary] = useState({ canceled: 0, partiallyReturned: 0 });
  const [totalRefundAmount, setTotalRefundAmount] = useState(0);

  const loadOrders = () => {
    request
      .get("bill", {
        params: {
          page: currentPage,
          sizePage: pageSize,
          status: [7, 8],
          code: searchValue,
          fromDate: selectedDates?.fromDate,
          toDate: selectedDates?.toDate,
        },
      })
      .then((response) => {
        const { data, totalPages } = response;
        setListOrder(data || []);
        setTotalPages(totalPages || 0);

        // Calculate total refund amount
        const totalRefund = (data || []).reduce((sum, order) => {
          return sum + ((order.totalMoney || 0) + (order.moneyShip || 0));
        }, 0);
        setTotalRefundAmount(totalRefund);
      })
      .catch((e) => {
        console.error("Error fetching orders:", e);
      });
  };

  const loadSummary = () => {
    request
      .get("/bill/statistic-bill-status")
      .then((response) => {
        const canceled = response.find((item) => item.status === 7)?.totalCount || 0;
        const partiallyReturned = response.find((item) => item.status === 8)?.totalCount || 0;
        setSummary({ canceled, partiallyReturned });
      })
      .catch((e) => {
        console.error("Error fetching summary:", e);
      });
  };

  const handleDateChange = (dates) => {
    if (dates) {
      setSelectedDates({
        fromDate: dates[0].format("YYYY-MM-DD"),
        toDate: dates[1].format("YYYY-MM-DD"),
      });
    } else {
      setSelectedDates(null);
    }
  };

  useEffect(() => {
    loadOrders();
    loadSummary();
  }, []);

  useEffect(() => {
    loadOrders();
  }, [currentPage, pageSize, searchValue, selectedDates]);

  const columns = [
    {
      title: "Order Code",
      dataIndex: "code",
      key: "code",
      render: (text, record) => (
        <Link to={`/admin/bill/${record.id}`}>
          <Tooltip title="View Details">{text}</Tooltip>
        </Link>
      ),
    },
    {
      title: "Customer Name",
      dataIndex: "customer",
      key: "customer",
      render: (x) => (x ? x : "Walk-in Customer"),
    },
    {
      title: "Phone Number",
      dataIndex: "phoneNumber",
      key: "phoneNumber",
      render: (x) => (x ? x : "-"),
    },
    {
      title: "Total Amount",
      dataIndex: "totalMoney",
      key: "totalMoney",
      render: (x, record) => {
        const totalAmount = (x || 0) + (record.moneyShip || 0);
        return (
          <span className="fw-semibold text-danger">
            <FormatCurrency value={totalAmount} />
          </span>
        );
      },
    },
    {
      title: "Date Created",
      dataIndex: "createAt",
      key: "createAt",
      render: (date) => <FormatDate date={date} />,
    },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
      render: (status) =>
        status === 7 ? (
          <Badge status="error" text="Canceled" />
        ) : status === 8 ? (
          <Badge status="warning" text="Partially Returned" />
        ) : (
          <Badge status="default" text="Unknown Status" />
        ),
    },
  ];

  return (
    <div>
      <Row gutter={16} className="mb-4">
        <Col span={12}>
          <Card>
            <h6>Canceled Orders</h6>
            <p>
              <Badge status="error" /> {summary.canceled} orders
            </p>
          </Card>
        </Col>
        <Col span={12}>
          <Card>
            <h6>Partially Returned Orders</h6>
            <p>
              <Badge status="warning" /> {summary.partiallyReturned} orders
            </p>
          </Card>
        </Col>
        <Col span={24}>
          <Card>
            <h6>Total Refund Amount</h6>
            <p className="text-danger">
              <FormatCurrency value={totalRefundAmount} />
            </p>
          </Card>
        </Col>
      </Row>

      <div className="d-flex justify-content-between align-items-center mb-4">
        <h6>Return Products Management</h6>
        <div>
          <Input
            className="me-2"
            onChange={(e) => setSearchValue(e.target.value)}
            placeholder="Search by code, name, or phone..."
            style={{ width: "440px" }}
          />
          <RangePicker onChange={handleDateChange} />
        </div>
      </div>
      <Table
        dataSource={listOrder}
        columns={columns}
        rowKey="id"
        pagination={{
          showSizeChanger: true,
          current: currentPage,
          pageSize: pageSize,
          pageSizeOptions: [5, 10, 20, 50],
          total: totalPages * pageSize,
          onChange: (page, pageSize) => {
            setCurrentPage(page);
            setPageSize(pageSize);
          },
        }}
      />
    </div>
  );
};

export default ReturnProduct;
