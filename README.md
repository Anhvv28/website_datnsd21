# Nhìn gì mà nhìn
CREATE DATABASE SD21_DUANTN10;
GO
USE SD21_DUANTN10;
-- 1
CREATE TABLE nguoi_dung (
    id INT IDENTITY(1,1) PRIMARY KEY,   
    tai_khoan NVARCHAR(255) NOT NULL,   
    mat_khau NVARCHAR(255) NOT NULL,    
    email NVARCHAR(255) unique,                
    ho_ten NVARCHAR(255),               
    sdt NVARCHAR(20),                  
    ngay_sinh DATE,                     
    gioi_tinh NVARCHAR(50),             
    cccd NVARCHAR(50),                  
    trangThai INT              
);
go
-- 2
CREATE TABLE nhan_vien (
    id INT IDENTITY(1,1) PRIMARY KEY,   
    NguoiDung_Id INT,                   
    ma_nv NVARCHAR(50),                
    vai_tro NVARCHAR(255),             
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),            
    nguoi_cap_nhat NVARCHAR(255),      
    lan_cap_nhat_cuoi DATETIME,         
    trangThai INT NOT NULL,            
    FOREIGN KEY (NguoiDung_Id) REFERENCES nguoi_dung(id)
);
go
-- 3
CREATE TABLE khach_hang (
    id INT IDENTITY(1,1) PRIMARY KEY,   
    dia_chi NVARCHAR(255),              
    email NVARCHAR(255),               
    sdt NVARCHAR(20),                   
    tong_tien DECIMAL(18, 2),          
    ngay_xac_nhan DATETIME,            
    ngay_van_chuyen DATETIME,           
    ngay_hoan_thanh DATETIME,           
    loai_hoa_Don NVARCHAR(255),        
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),            
    ghi_chu NVARCHAR(255),              
    trang_thai INT NOT NULL            
);
GO
--4
CREATE TABLE Kich_Co (
    id INT PRIMARY KEY IDENTITY(1,1),
    Ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 5
CREATE TABLE Mau_Sac (
    id INT PRIMARY KEY IDENTITY(1,1),
    Ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 6
CREATE TABLE ThuongHieu (
    id INT PRIMARY KEY IDENTITY(1,1),
    Ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 7
CREATE TABLE DeGiay (
    id INT PRIMARY KEY IDENTITY(1,1),
    Ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 8
CREATE TABLE ChatLieu (
    id INT PRIMARY KEY IDENTITY(1,1),
    Ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
--9
CREATE TABLE Loai (
    id INT PRIMARY KEY IDENTITY(1,1),
    Ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 10
CREATE TABLE SanPham (
    Id_SanPham INT PRIMARY KEY IDENTITY(1,1),
    Ma_San_Pham NVARCHAR(50) NOT NULL,
    Ten_San_Pham NVARCHAR(255) NOT NULL,
    Ngay_Tao DATETIME DEFAULT GETDATE(),
    Nguoi_Tao NVARCHAR(255),
    Lan_Cap_Nhat_Cuoi DATETIME NULL,
    TrangThai INT
);
GO
-- 11
CREATE TABLE SanPhamChiTiet (
    id INT PRIMARY KEY IDENTITY(1,1),
    SanPham_Id INT FOREIGN KEY REFERENCES SanPham(Id_SanPham),
    ThuongHieu_Id INT FOREIGN KEY REFERENCES ThuongHieu(id),
    ChatLieu_Id INT FOREIGN KEY REFERENCES ChatLieu(id),
    DeGiay_Id INT FOREIGN KEY REFERENCES DeGiay(id),
    Loai_Id INT FOREIGN KEY REFERENCES Loai(id),
    Ma_San_Pham_CT NVARCHAR(50) NOT NULL,
    mo_ta NVARCHAR(MAX),
    so_luong INT NOT NULL,
    gioi_tinh NVARCHAR(10),
    kich_co NVARCHAR(50),
    mau_sac NVARCHAR(50),
    gia_tien DECIMAL(18,2),
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL,
    nguoi_cap_nhat NVARCHAR(255),
    trang_thai NVARCHAR(50)
);
go
-- 12
CREATE TABLE HoaDon (
    id INT IDENTITY(1,1) PRIMARY KEY,            
    NhanVien_Id INT,                             
    khachHang_Id INT,                            
    dia_chi NVARCHAR(255),                       
    email NVARCHAR(255),                         
    sdt NVARCHAR(20),                            
    tong_tien DECIMAL(18, 2) NOT NULL,           
    ngay_xac_nhan DATETIME,                      
    ngay_van_chuyen DATETIME,                   
    ngay_hoan_thanh DATETIME,                    
    loai_hoa_don NVARCHAR(255),                  
    ngay_tao DATETIME DEFAULT GETDATE(),         
    nguoi_tao NVARCHAR(255),                     
    ghi_chu NVARCHAR(255),                       
    trang_thai INT NOT NULL,                     
    FOREIGN KEY (NhanVien_Id) REFERENCES nhan_vien(id),  
    FOREIGN KEY (khachHang_Id) REFERENCES khach_hang(id)
);
go
--13
CREATE TABLE hoa_don_chi_tiet (
    id INT PRIMARY KEY IDENTITY(1,1),
    SanPhamCT_Id INT FOREIGN KEY REFERENCES SanPhamChiTiet(id),
    HoaDon_Id INT FOREIGN KEY REFERENCES HoaDon(id),
    don_gia MONEY,
    so_luong INT,
    trangThai INT not null
);
go
-- 14
CREATE TABLE DotGiamGia (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_dot NVARCHAR(255) NOT NULL,
    gia_tri_giam DECIMAL(10, 2) NOT NULL,
    ngay_bat_dau DATE NOT NULL,
    ngay_ket_thuc DATE NOT NULL,
    nguoi_tao NVARCHAR(255),
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME,
    trangThai INT NOT NULL
);
go
-- 15
CREATE TABLE SanPham_DotGiam (
    Id INT IDENTITY(1,1) PRIMARY KEY,   
    CTSP_Id INT,                        
    DotGiam_Id INT,                     
    FOREIGN KEY (CTSP_Id) REFERENCES SanPhamChiTiet(id),      
    FOREIGN KEY (DotGiam_Id) REFERENCES DotGiamGia(id)      
);
go
-- 16
CREATE TABLE PhieuGiam (
    id INT PRIMARY KEY IDENTITY(1,1),
    Ma_giam VARCHAR(50),
    gia_tri_giam_max MONEY,
    gia_tri_giam MONEY,
    so_luong INT,
    ngay_bat_dau DATETIME,
    ngay_ket_thuc DATETIME,
    nguoi_tao VARCHAR(50),
    ngay_tao DATETIME,
    nguoi_cap_nhat VARCHAR(50),
    lan_cap_nhat_cuoi DATETIME,
    trangThai INT  NOT Null,
);
go
-- 17
CREATE TABLE phieu_giam_gia_chi_tiet (
    Id INT IDENTITY(1,1) PRIMARY KEY,   
    HoaDon_Id INT,                      
    PhieuGiam_Id INT,                
    FOREIGN KEY (HoaDon_Id) REFERENCES HoaDon(id),             
    FOREIGN KEY (PhieuGiam_Id) REFERENCES PhieuGiam(id)
);
go
-- 18
CREATE TABLE KhachHang_PhieuGiam (
    Id INT PRIMARY KEY IDENTITY(1,1),
    KhachHang_Id INT FOREIGN KEY REFERENCES khach_hang(id),
    PhieuGiam_Id INT FOREIGN KEY REFERENCES PhieuGiam(id)
);
go
-- 19
CREATE TABLE dia_chi (
    id INT IDENTITY(1,1) PRIMARY KEY,  
    NguoiDung_Id INT,                  
    so_nha_ten_pho NVARCHAR(255),       
    xa_phuong NVARCHAR(255),            
    quan_huyen NVARCHAR(255),           
    tinh_thanh_pho NVARCHAR(255),       
    ten_nguoi_nhan NVARCHAR(255),       
    ma_buu_chinh NVARCHAR(50),          
    FOREIGN KEY (NguoiDung_Id) REFERENCES nguoi_dung(id) 
);
-- 20
go
-- B?ng Gio_hang
CREATE TABLE Gio_hang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    TaiKhoan_Id INT FOREIGN KEY REFERENCES nguoi_dung(id),
    ngay_tao DATETIME DEFAULT GETDATE(),
    trangThai INT NOT NULL
);
GO
-- 21
-- B?ng Gio_hang_chi_tiet
CREATE TABLE Gio_hang_chi_tiet (
    id INT IDENTITY(1,1) PRIMARY KEY,
    CTSP_Id INT FOREIGN KEY REFERENCES SanPhamChiTiet(id),
    GioHang_Id INT FOREIGN KEY REFERENCES Gio_hang(id),
    soluong INT NOT NULL,
    ngay_tao DATETIME DEFAULT GETDATE(),
    trangThai INT NOT NULL
);
GO
-- 22
GO

-- B?ng lich_su_hoa_don
CREATE TABLE lich_su_hoa_don (
    id INT IDENTITY(1,1) PRIMARY KEY,
    HoaDon_Id INT FOREIGN KEY REFERENCES HoaDon(id),
    NhanVien_Id INT FOREIGN KEY REFERENCES nhan_vien(id),
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME,
    trangThai INT NOT NULL
);
GO
-- 23
-- B?ng phuong_thuc_thanh_toan
CREATE TABLE phuong_thuc_thanh_toan (
    id INT IDENTITY(1,1) PRIMARY KEY,
    HoaDon_Id INT FOREIGN KEY REFERENCES HoaDon(id),
    ten_phuong_thuc NVARCHAR(255) NOT NULL
);
GO 
-- 24
CREATE TABLE SanPham_yeuThich (
    Id INT PRIMARY KEY IDENTITY(1,1),
    SanPham_Id INT FOREIGN KEY REFERENCES SanPham(Id_SanPham), -- Gi? s? có b?ng SanPham
    MaKh_Id INT FOREIGN KEY REFERENCES khach_hang(id)
);
-- 25
CREATE TABLE hinh_anh (
    id INT PRIMARY KEY IDENTITY(1,1),
    SanPham_Id INT FOREIGN KEY REFERENCES SanPham(Id_SanPham),
    ten_anh NVARCHAR(255),
    duong_dan NVARCHAR(255), -- Ðu?ng d?n d?n file hình ?nh
    ngay_tao DATETIME,
    nguoi_tao NVARCHAR(50),
    nguoi_cap_nhat NVARCHAR(50),
    lan_cap_nhat_cuoi DATETIME,
    trang_thai BIT
);
go

--- INSERT INTO
INSERT INTO nguoi_dung (tai_khoan, mat_khau, email, ho_ten, sdt, ngay_sinh, gioi_tinh, cccd, trangThai)
VALUES
('user1', 'password1', 'user1@gmail.com', 'Nguyen Van A', '0123456789', '1990-01-01', 'Nam', '123456789', 1),
('user2', 'password2', 'user2@gmail.com', 'Le Thi B', '0987654321', '1992-02-02', 'Nu', '987654321', 1);
go
INSERT INTO nhan_vien (NguoiDung_Id, ma_nv, vai_tro, nguoi_tao, nguoi_cap_nhat, lan_cap_nhat_cuoi, trangThai)
VALUES
(1, 'NV001', 'Admin', 'admin', 'admin', GETDATE(), 1),
(2, 'NV002', 'Sales', 'admin', 'admin', GETDATE(), 1);
go
INSERT INTO khach_hang (dia_chi, email, sdt, tong_tien, ngay_xac_nhan, ngay_van_chuyen, ngay_hoan_thanh, loai_hoa_don, nguoi_tao, ghi_chu, trang_thai)
VALUES
('123 Le Loi', 'customer1@gmail.com', '012345678', 500000, '2024-10-01', '2024-10-02', '2024-10-03', 'COD', 'admin', 'Ghi chú 1', 1),
('456 Tran Phu', 'customer2@gmail.com', '098765432', 750000, '2024-10-05', '2024-10-06', '2024-10-07', 'Online', 'admin', 'Ghi chú 2', 1);
go
INSERT INTO Kich_Co (Ten, trang_thai, nguoi_tao, nguoi_cap_nhat, lan_cap_nhat_cuoi)
VALUES
('Size 38', 1, 'admin', 'admin', GETDATE()),
('Size 40', 1, 'admin', 'admin', GETDATE());
go
INSERT INTO Mau_Sac (Ten, trang_thai, nguoi_tao, nguoi_cap_nhat, lan_cap_nhat_cuoi)
VALUES
('Đỏ', 1, 'admin', 'admin', GETDATE()),
('Đen', 1, 'admin', 'admin', GETDATE());
go
INSERT INTO ThuongHieu (Ten, trang_thai, nguoi_tao, nguoi_cap_nhat, lan_cap_nhat_cuoi)
VALUES
('Nike', 1, 'admin', 'admin', GETDATE()),
('Adidas', 1, 'admin', 'admin', GETDATE());
go
INSERT INTO DeGiay (Ten, trang_thai, nguoi_tao, nguoi_cap_nhat, lan_cap_nhat_cuoi)
VALUES
('Đế cao su', 1, 'admin', 'admin', GETDATE()),
('Đế nhựa', 1, 'admin', 'admin', GETDATE());
go
INSERT INTO ChatLieu (Ten, trang_thai, nguoi_tao, nguoi_cap_nhat, lan_cap_nhat_cuoi)
VALUES
('Da tổng hợp', 1, 'admin', 'admin', GETDATE()),
('Vải', 1, 'admin', 'admin', GETDATE());
go
INSERT INTO Loai (Ten, trang_thai, nguoi_tao, nguoi_cap_nhat, lan_cap_nhat_cuoi)
VALUES
('Giày thể thao', 1, 'admin', 'admin', GETDATE()),
('Giày thời trang', 1, 'admin', 'admin', GETDATE());
go
INSERT INTO SanPham (Ma_San_Pham, Ten_San_Pham, Nguoi_Tao, TrangThai)
VALUES
('SP001', 'Giày Nike Air Max', 'admin', 1),
('SP002', 'Giày Adidas Superstar', 'admin', 1);
go
INSERT INTO SanPhamChiTiet (SanPham_Id, ThuongHieu_Id, ChatLieu_Id, DeGiay_Id, Loai_Id, Ma_San_Pham_CT, mo_ta, so_luong, gioi_tinh, kich_co, mau_sac, gia_tien, nguoi_tao, trang_thai)
VALUES
(1, 1, 1, 1, 1, 'SP001-RED-38', 'Giày Nike màu đỏ size 38', 100, 'Nam', 'Size 38', 'Đỏ', 2000000, 'admin', 'Còn hàng'),
(2, 2, 2, 2, 2, 'SP002-BLK-40', 'Giày Adidas màu đen size 40', 150, 'Nữ', 'Size 40', 'Đen', 1800000, 'admin', 'Còn hàng');
go
INSERT INTO HoaDon (NhanVien_Id, khachHang_Id, dia_chi, email, sdt, tong_tien, loai_hoa_don, nguoi_tao, ghi_chu, trang_thai)
VALUES
(1, 1, '123 Le Loi', 'customer1@gmail.com', '012345678', 2000000, 'COD', 'admin', 'Thanh toán thành công', 1),
(2, 2, '456 Tran Phu', 'customer2@gmail.com', '098765432', 1800000, 'Online', 'admin', 'Thanh toán thất bại', 1);
go
INSERT INTO DotGiamGia (ten_dot, gia_tri_giam, ngay_bat_dau, ngay_ket_thuc, nguoi_tao, trangThai)
VALUES
('Khuyến mãi cuối năm', 10.00, '2024-12-01', '2024-12-31', 'admin',1),
('Khuyến mãi Tết', 15.00, '2025-01-01', '2025-01-15', 'admin',1);
