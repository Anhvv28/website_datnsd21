import React, { useEffect, useState } from "react";
import Images from "../../static";
import InforMe from "./inforMe";
import Invoice from "../invoice";
import { useNavigate } from "react-router-dom";
import Address from "./address";
import ChangePassword from "./changePassword";
import ModalComponent from "../../components/Modal";
import { deleteToken, deleteUserToken } from "../../helper/useCookie";
import path from "../../constants/path";

const Information = () => {
  const [showModal, setShowMoal] = useState<boolean>(false);
  const [selectedCategory, setSelectedCategory] = useState("Tài khoản của tôi");
  const navigate = useNavigate();

  const dataAside = [
    {
      id: 1,
      name: "Tài khoản của tôi",
      img: Images.iconSetting,
    },
    {
      id: 2,
      name: "Đơn mua",
      img: Images.iconOrder,
    },
    {
      id: 3,
      name: "Địa chỉ",
      img: Images.iconAddress,
    },
    {
      id: 4,
      name: "Đổi mật khẩu",
      img: Images.iconChangePassword,
    },
    {
      id: 5,
      name: "Đăng xuất",
      img: Images.iconLogout,
    },
  ];

  const handleCategoryClick = (category: string) => setSelectedCategory(category);

  const handleLogout = () => {
    deleteToken();
    deleteUserToken();
    sessionStorage.removeItem("idAccount");
    navigate(path.loginScreen);
    window.location.reload();
  };

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="w-full min-h-screen bg-gray-50 flex">
      {/* Sidebar */}
      <aside className="h-screen w-64 bg-white shadow-lg flex flex-col py-4">
        <div className="text-center font-semibold text-lg text-gray-700 mb-6">
          Quản lý tài khoản
        </div>
        <div className="flex flex-col gap-4">
          {dataAside.map((item) => (
            <div
              key={item.id}
              onClick={() => handleCategoryClick(item.name)}
              className={`flex items-center gap-4 px-4 py-3 cursor-pointer rounded-lg ${
                selectedCategory === item.name
                  ? "bg-gray-200 text-black font-semibold"
                  : "hover:bg-gray-100 text-gray-600"
              }`}
            >
              <img
                src={item.img}
                alt={item.name}
                className="w-6 h-6 object-contain"
              />
              <span>{item.name}</span>
            </div>
          ))}
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-grow bg-white shadow-md rounded-lg p-6 mt-6 mx-6">
        {selectedCategory === "Tài khoản của tôi" ? (
          <InforMe />
        ) : selectedCategory === "Đơn mua" ? (
          <Invoice />
        ) : selectedCategory === "Địa chỉ" ? (
          <Address />
        ) : selectedCategory === "Đổi mật khẩu" ? (
          <ChangePassword />
        ) : (
          <div className="flex justify-center items-center h-full">
            <button
              onClick={() => setShowMoal(true)}
              className="bg-red-500 text-white px-6 py-2 rounded-full shadow-md hover:bg-red-600 transition duration-300"
            >
              Xác nhận đăng xuất ngay
            </button>
          </div>
        )}
      </main>

      {/* Modal */}
      <ModalComponent
  check={true} // or set a relevant boolean value based on your logic
  isVisible={showModal}
  onClose={() => setShowMoal(false)}
>
  <div className="flex flex-col items-center text-center">
    <svg
      className="w-16 h-16 text-red-500 mb-4"
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      stroke="currentColor"
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth={2}
        d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2V6m4 10H6m16 0a9 9 0 11-18 0 9 9 0 0118 0z"
      />
    </svg>
    <h3 className="text-lg font-medium text-gray-700 mb-4">
      Bạn có chắc chắn muốn đăng xuất?
    </h3>
    <div className="flex gap-6">
      <button
        onClick={() => setShowMoal(false)}
        className="bg-gray-200 px-6 py-2 rounded-lg text-gray-700 hover:bg-gray-300 transition duration-300"
      >
        Hủy
      </button>
      <button
        onClick={() => {
          handleLogout();
          setShowMoal(false);
        }}
        className="bg-red-500 text-white px-6 py-2 rounded-lg hover:bg-red-600 transition duration-300"
      >
        Đăng xuất
      </button>
    </div>
  </div>
</ModalComponent>

    </div>
  );
};

export default Information;
