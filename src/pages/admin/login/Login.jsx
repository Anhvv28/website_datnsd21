import axios from "axios";
import { jwtDecode } from "jwt-decode"; // Corrected named import
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { setToken, setUserToken } from "~/helper/useCookies";
import './login.css'; // Ensure the CSS file name is correct

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({ email: "", password: "" });
  const [isLoading, setIsLoading] = useState(false); // Optional: For loading state

  // Email validation function
  const validateEmail = (email) => {
    const re = /\S+@\S+\.\S+/;
    return re.test(email);
  };

  // Password validation function
  const validatePassword = (password) => {
    const re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
    return re.test(password);
  };

  // Login function
  const login = async () => {
    // Reset errors
    setErrors({ email: "", password: "" });

    // Validate email and password before calling API
    let valid = true;
    if (!validateEmail(email)) {
      setErrors((prevErrors) => ({ ...prevErrors, email: "Địa chỉ email không hợp lệ." }));
      valid = false;
    }
    // if (!validatePassword(password)) {
    //   setErrors((prevErrors) => ({ ...prevErrors, password: "Mật khẩu phải có ít nhất 6 ký tự, gồm cả chữ và số." }));
    //   valid = false;
    // }
    if (!valid) return;

    setIsLoading(true); // Optional: Start loading

    try {
      const res = await axios.post(`http://localhost:8080/login-v2/singin`, { email, password });

      if (res.status === 200 || res.status === 201) { // Typically 200 or 201 for successful POST
        const decodedToken = jwtDecode(res.data.token);
        if (decodedToken.role === 'ROLE_USER') {
          toast.error('Bạn không có quyền truy cập tính năng này!');
        } else {
          toast.success("Đăng nhập thành công");
          setToken(res.data.token); // Save token to cookies
          setUserToken(res.data.token); // Save user token
          sessionStorage.setItem("idAccount", decodedToken.id); // Save user ID
          navigate("/"); // Redirect after successful login
        }
      } else {
        toast.error('Đăng nhập không thành công. Vui lòng thử lại.');
      }
    } catch (error) {
      // Handle different error scenarios
      if (error.response) {
        // Server responded with a status other than 2xx
        toast.error(error.response.data.message || 'Đăng nhập thất bại.');
      } else if (error.request) {
        // Request was made but no response received
        toast.error('Không thể kết nối đến máy chủ.');
      } else {
        // Something else happened
        toast.error('Đã xảy ra lỗi.');
      }
    } finally {
      setIsLoading(false); // Optional: End loading
    }
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevents the default form submission (page reload)
    login(); // Calls the login function
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h3 className="text-center">Đăng nhập</h3>
        {/* Wrap inputs and button in a form */}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Tài khoản</label>
            <input
              type="email"
              className={`form-control ${errors.email ? 'is-invalid' : ''}`}
              placeholder="Nhập email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              aria-describedby="emailHelp"
            />
            {errors.email && <div className="invalid-feedback">{errors.email}</div>}
          </div>
          <div className="mb-3">
            <label className="form-label">Mật khẩu</label>
            <input
              type="password"
              className={`form-control ${errors.password ? 'is-invalid' : ''}`}
              placeholder="Nhập mật khẩu"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              aria-describedby="passwordHelp"
            />
            {errors.password && <div className="invalid-feedback">{errors.password}</div>}
          </div>
          <div className="mb-3">
            {/* Change button type to submit */}
            <button type="submit" className="btn btn-primary w-100" disabled={isLoading}>
              {isLoading ? 'Đang đăng nhập...' : 'Đăng nhập'}
            </button>
          </div>
        </form>
        <a href="/forgot" className="forgot-password">
          <i className="fas fa-key"></i> Quên mật khẩu?
        </a>
      </div>
    </div>
  );
};

export default Login;
