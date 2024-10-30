
CREATE DATABASE SD21_DUANTN25;
GO 
USE SD21_DUANTN25;
go
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
    trang_thai INT              
);
go
-- 2
CREATE TABLE nhan_vien (
    id INT IDENTITY(1,1) PRIMARY KEY,   
    nguoi_dung_id INT,                   
    ma_nv NVARCHAR(50),                
    vai_tro NVARCHAR(255),             
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),            
    nguoi_cap_nhat NVARCHAR(255),      
    lan_cap_nhat_cuoi DATETIME,         
    trang_thai INT NOT NULL,            
    FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id)
);
go
-- 3
CREATE TABLE khach_hang (
    id INT IDENTITY(1,1) PRIMARY KEY,   
	nguoi_dung_id INT,     
    dia_chi NVARCHAR(255),              
    email NVARCHAR(255),               
    sdt NVARCHAR(20),                                 
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),            
    ghi_chu NVARCHAR(255),              
    trang_thai INT NOT NULL   
	FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id)
);
GO
--4
CREATE TABLE kich_co (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 5
CREATE TABLE mau_sac (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 6
CREATE TABLE thuong_hieu (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 7
CREATE TABLE de_giay (
    id INT PRIMARY KEY IDENTITY(1,1),
    ten NVARCHAR(50) NOT NULL,
    trang_thai int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL
);
GO
-- 8
CREATE TABLE chat_lieu (
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
CREATE TABLE loai (
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
CREATE TABLE san_pham (
    id INT PRIMARY KEY IDENTITY(1,1),
    ma_san_pham NVARCHAR(50) NOT NULL,
    ten_san_pham NVARCHAR(255) NOT NULL,
	so_luong INT,
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL,
    trang_thai INT
);
GO
-- 11
CREATE TABLE san_pham_chi_tiet (
    id INT PRIMARY KEY IDENTITY(1,1),
    san_pham_id INT FOREIGN KEY REFERENCES san_pham(id),
    thuong_hieu_id INT FOREIGN KEY REFERENCES thuong_hieu(id),
    chat_lieu_id INT FOREIGN KEY REFERENCES chat_lieu(id),
    de_giay_id INT FOREIGN KEY REFERENCES de_giay(id),
    loai_id INT FOREIGN KEY REFERENCES loai(id),
	kich_co_id INT FOREIGN KEY REFERENCES kich_co(id),
    mau_sac_id INT FOREIGN KEY REFERENCES mau_sac(id),
    ma_spct NVARCHAR(50) NOT NULL,
    mo_ta NVARCHAR(MAX),
    so_luong INT NOT NULL,
    gioi_tinh NVARCHAR(10),
    gia_tien DECIMAL(18,2),
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME NULL,
    nguoi_cap_nhat NVARCHAR(255),
    trang_thai NVARCHAR(50)
);
go
CREATE TABLE hinh_anh (
    id INT PRIMARY KEY IDENTITY(1,1),
    spct_id INT FOREIGN KEY REFERENCES san_pham_chi_tiet(id),
    ten_anh NVARCHAR(255),
    duong_dan NVARCHAR(255), -- Ðu?ng d?n d?n file hình ?nh
    ngay_tao DATETIME,
    nguoi_tao NVARCHAR(50),
    nguoi_cap_nhat NVARCHAR(50),
    lan_cap_nhat_cuoi DATETIME,
    trang_thai INT
);
go
-- 12
CREATE TABLE hoa_don (
    id INT IDENTITY(1,1) PRIMARY KEY,            
    nhan_vien_id INT,                             
    khach_hang_id INT,                            
    dia_chi NVARCHAR(255),                       
    email NVARCHAR(255),                         
    sdt NVARCHAR(20),                            
    tong_tien DECIMAL(18, 2) NOT NULL,           
    ngay_xac_nhan DATETIME,                      
    ngay_van_chuyen DATETIME,                   
    ngay_hoan_thanh DATETIME,
	ngay_huy DATETIME,     
    loai_hoa_don NVARCHAR(255),                  
    ngay_tao DATETIME DEFAULT GETDATE(),         
    nguoi_tao NVARCHAR(255),                     
    ghi_chu NVARCHAR(255),                       
    trang_thai INT NOT NULL,                     
    FOREIGN KEY (nhan_vien_id) REFERENCES nhan_vien(id),  
    FOREIGN KEY (khach_hang_id) REFERENCES khach_hang(id)
);
go
--13
CREATE TABLE hoa_don_chi_tiet (
    id INT PRIMARY KEY IDENTITY(1,1),
    spct_id INT FOREIGN KEY REFERENCES san_pham_chi_tiet(id),
    hoa_don_id INT FOREIGN KEY REFERENCES hoa_don(id),
    don_gia MONEY,
    so_luong INT,
    trang_thai INT not null
);
go
-- 14
CREATE TABLE dot_giam_gia (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_dot NVARCHAR(255) NOT NULL,
    gia_tri_giam DECIMAL(10, 2) NOT NULL,
    ngay_bat_dau DATE NOT NULL,
    ngay_ket_thuc DATE NOT NULL,
    nguoi_tao NVARCHAR(255),
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME,
    trang_thai INT NOT NULL
);
go
-- 15
CREATE TABLE san_pham_dot_giam (
    id INT IDENTITY(1,1) PRIMARY KEY,   
    spct_id INT,                        
    dot_giam_id INT,                     
    FOREIGN KEY (spct_id) REFERENCES san_pham_chi_tiet(id),      
    FOREIGN KEY (dot_giam_id) REFERENCES dot_giam_gia(id)      
);
go
-- 16
CREATE TABLE phieu_giam (
    id INT PRIMARY KEY IDENTITY(1,1),
    ma_giam VARCHAR(50),
    gia_tri_giam_max MONEY,
    gia_tri_giam MONEY,
    so_luong INT,
    ngay_bat_dau DATETIME,
    ngay_ket_thuc DATETIME,
    nguoi_tao VARCHAR(50),
    ngay_tao DATETIME,
    nguoi_cap_nhat VARCHAR(50),
    lan_cap_nhat_cuoi DATETIME,
    trang_thai INT  NOT Null,
);
go
-- 17
CREATE TABLE phieu_giam_gia_chi_tiet (
    id INT IDENTITY(1,1) PRIMARY KEY,   
    hoa_don_id INT,                      
    phieu_giam_id INT,                
    FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id),             
    FOREIGN KEY (phieu_giam_id) REFERENCES phieu_giam(id)
);
go
-- 18
CREATE TABLE khach_hang_phieu_giam (
    id INT PRIMARY KEY IDENTITY(1,1),
    khach_hang_id INT FOREIGN KEY REFERENCES khach_hang(id),
    phieu_giam_id INT FOREIGN KEY REFERENCES phieu_giam(id)
);
go
-- 19
CREATE TABLE dia_chi (
    id INT IDENTITY(1,1) PRIMARY KEY,  
    nguoi_dung_id INT,                  
    so_nha_ten_pho NVARCHAR(255),       
    xa_phuong NVARCHAR(255),            
    quan_huyen NVARCHAR(255),           
    tinh_thanh_pho NVARCHAR(255),       
    ten_nguoi_nhan NVARCHAR(255),       
    ma_buu_chinh NVARCHAR(50),          
    FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id) 
);
-- 20
go
-- B?ng Gio_hang
go
CREATE TABLE gio_hang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nguoi_dung_id INT FOREIGN KEY REFERENCES nguoi_dung(id),
    ngay_tao DATETIME DEFAULT GETDATE(),
    trang_thai INT NOT NULL
);

go
-- 21
-- B?ng Gio_hang_chi_tiet
CREATE TABLE gio_hang_chi_tiet (
    id INT IDENTITY(1,1) PRIMARY KEY,
    spct_id INT FOREIGN KEY REFERENCES san_pham_chi_tiet(id),
    gio_hang_id INT FOREIGN KEY REFERENCES gio_hang(id),
    soluong INT NOT NULL,
    ngay_tao DATETIME DEFAULT GETDATE(),
    trang_thai INT NOT NULL
);
GO
-- 22


-- B?ng lich_su_hoa_don
CREATE TABLE lich_su_hoa_don (
    id INT IDENTITY(1,1) PRIMARY KEY,
    hoa_don_id INT FOREIGN KEY REFERENCES hoa_don(id),
    nhan_vien_id INT FOREIGN KEY REFERENCES nhan_vien(id),
    ngay_tao DATETIME DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    lan_cap_nhat_cuoi DATETIME,
    trang_thai INT NOT NULL
);
GO
-- 23
-- B?ng phuong_thuc_thanh_toan
CREATE TABLE phuong_thuc_thanh_toan (
    id INT IDENTITY(1,1) PRIMARY KEY,
    hoa_don_id INT FOREIGN KEY REFERENCES hoa_don(id),
    ten_phuong_thuc NVARCHAR(255) NOT NULL
);
GO 
-- 24
CREATE TABLE san_pham_yeu_thich (
    id INT PRIMARY KEY IDENTITY(1,1),
	nguoi_dung_id INT,
    spct_id INT FOREIGN KEY REFERENCES san_pham_chi_tiet(id),
    FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id) 
);

go


-- Insert into nguoi_dung
INSERT INTO nguoi_dung (tai_khoan, mat_khau, email, ho_ten, sdt, ngay_sinh, gioi_tinh, cccd, trang_thai)
VALUES
('user1', 'password1', 'user1@example.com', 'Nguyen Van A', '0901234567', '1990-01-01', 'Nam', '123456789', 1),
('user2', 'password2', 'user2@example.com', 'Tran Thi B', '0912345678', '1992-02-02', 'Nu', '987654321', 1);

-- Insert into nhan_vien
INSERT INTO nhan_vien (nguoi_dung_id, ma_nv, vai_tro, ngay_tao, nguoi_tao, trang_thai)
VALUES
(1, 'NV001', 'Admin', GETDATE(), 'admin', 1);

-- Insert into khach_hang
INSERT INTO khach_hang (nguoi_dung_id, dia_chi, email, sdt, ngay_tao, nguoi_tao, trang_thai)
VALUES
(2, '123 ABC Street', 'user2@example.com', '0912345678', GETDATE(), 'admin', 1);

-- Insert into kich_co
INSERT INTO kich_co (ten, trang_thai, ngay_tao, nguoi_tao)
VALUES
('39', 1, GETDATE(), 'admin'),
('40', 1, GETDATE(), 'admin'),
('41', 1, GETDATE(), 'admin'),
('42', 1, GETDATE(), 'admin'),
('43', 1, GETDATE(), 'admin');

-- Insert into mau_sac
INSERT INTO mau_sac (ten, trang_thai, ngay_tao, nguoi_tao)
VALUES
('Red', 1, GETDATE(), 'admin'),
('Black', 1, GETDATE(), 'admin'),
('Blue', 1, GETDATE(), 'admin'),
('White', 1, GETDATE(), 'admin'),
('Green', 1, GETDATE(), 'admin');

-- Insert into thuong_hieu
INSERT INTO thuong_hieu (ten, trang_thai, ngay_tao, nguoi_tao)
VALUES
('Nike', 1, GETDATE(), 'admin'),
('Adidas', 1, GETDATE(), 'admin'),
('Puma', 1, GETDATE(), 'admin');


-- Insert into de_giay
INSERT INTO de_giay (ten, trang_thai, ngay_tao, nguoi_tao)
VALUES
('Rubber', 1, GETDATE(), 'admin'),
('Foam', 1, GETDATE(), 'admin'),
('Synthetic', 1, GETDATE(), 'admin');

-- Insert into chat_lieu
INSERT INTO chat_lieu (ten, trang_thai, ngay_tao, nguoi_tao)
VALUES
('Leather', 1, GETDATE(), 'admin'),
('Synthetic', 1, GETDATE(), 'admin'),
('Canvas', 1, GETDATE(), 'admin');

-- Insert into loai
INSERT INTO loai (ten, trang_thai, ngay_tao, nguoi_tao)
VALUES
('Running', 1, GETDATE(), 'admin'),
('Casual', 1, GETDATE(), 'admin'),
('Sports', 1, GETDATE(), 'admin');

-- Insert into san_pham
INSERT INTO san_pham (ma_san_pham, ten_san_pham, so_luong, ngay_tao, nguoi_tao, trang_thai)
VALUES
('SP001', 'Nike Air Max', 50, GETDATE(), 'admin', 1),
('SP002', 'Nike Air Max', 50, GETDATE(), 'admin', 1),
('SP003', 'Adidas Superstar', 40, GETDATE(), 'admin', 1),
('SP004', 'Adidas Superstar', 40, GETDATE(), 'admin', 1),
('SP005', 'Puma Speed', 30, GETDATE(), 'admin', 1);

-- Insert into san_pham_chi_tiet
INSERT INTO san_pham_chi_tiet (san_pham_id, thuong_hieu_id, chat_lieu_id, de_giay_id, loai_id, kich_co_id, mau_sac_id, ma_spct, mo_ta, so_luong, gioi_tinh, gia_tien, ngay_tao, nguoi_tao, trang_thai)
VALUES
(1, 1, 1, 1, 1, 1, 1, 'SPCT001', 'Giày Nike Air Max Đỏ', 50, 'Unisex', 100.50, GETDATE(), 'admin', 'Available'),
(2, 1, 1, 1, 1, 2, 2, 'SPCT002', 'Giày Nike Air Max Đen', 50, 'Unisex', 100.50, GETDATE(), 'admin', 'Available'),
(3, 2, 2, 2, 2, 3, 3, 'SPCT003', 'Giày Adidas Superstar Xanh', 40, 'Unisex', 85.00, GETDATE(), 'admin', 'Available'),
(4, 2, 2, 2, 2, 4, 4, 'SPCT004', 'Giày Adidas Superstar Đen', 40, 'Unisex', 85.00, GETDATE(), 'admin', 'Available'),
(5, 3, 3, 3, 3, 5, 5, 'SPCT005', 'Giày Puma Speed Trắng', 30, 'Unisex', 90.00, GETDATE(), 'admin', 'Available');

-- Insert into hinh_anh
INSERT INTO hinh_anh (spct_id, ten_anh, duong_dan, ngay_tao, nguoi_tao, trang_thai)
VALUES
(1, 'nike_air_max_red.jpg', '/images/product-1.jpg', GETDATE(), 'admin', 1),
(2, 'nike_air_max_black.jpg', '/images/product-2.jpg', GETDATE(), 'admin', 1),
(4, 'nike_air_max_black.jpg', '/images/product-4.jpg', GETDATE(), 'admin', 1),
(5, 'nike_air_max_black.jpg', '/images/product-5.jpg', GETDATE(), 'admin', 1),
(3, 'adidas_superstar_blue.jpg', '/images/product-3_superstar_blue.jpg', GETDATE(), 'admin', 1);

-- Insert into hoa_don
INSERT INTO hoa_don (nhan_vien_id, khach_hang_id, dia_chi, email, sdt, tong_tien, loai_hoa_don, ngay_tao, nguoi_tao, trang_thai)
VALUES
(1, 1, '123 ABC Street', 'user2@example.com', '0912345678', 500.00, 'Online', GETDATE(), 'admin', 1);

-- Insert into hoa_don_chi_tiet
INSERT INTO hoa_don_chi_tiet (spct_id, hoa_don_id, don_gia, so_luong, trang_thai)
VALUES
(1, 1, 100.50, 2, 1),
(3, 1, 100.50, 2, 1),
(5, 1, 100.50, 2, 1),
(2, 1, 100.50, 3, 1);

-- Insert into dot_giam_gia
INSERT INTO dot_giam_gia (ten_dot, gia_tri_giam, ngay_bat_dau, ngay_ket_thuc, nguoi_tao, ngay_tao, trang_thai)
VALUES
('Giảm Giá Cuối Năm', 10.00, '2024-11-01', '2024-12-01', 'admin', GETDATE(), 1);

-- Insert into san_pham_dot_giam
INSERT INTO san_pham_dot_giam (spct_id, dot_giam_id)
VALUES
(1, 1);

-- Insert into phieu_giam
INSERT INTO phieu_giam (ma_giam, gia_tri_giam_max, gia_tri_giam, so_luong, ngay_bat_dau, ngay_ket_thuc, nguoi_tao, ngay_tao, trang_thai)
VALUES
('GIAM10', 50.00, 10.00, 10, '2024-11-01', '2024-12-01', 'admin', GETDATE(), 1);

-- Insert into phieu_giam_gia_chi_tiet
INSERT INTO phieu_giam_gia_chi_tiet (hoa_don_id, phieu_giam_id)
VALUES
(1, 1);

-- Insert into khach_hang_phieu_giam
INSERT INTO khach_hang_phieu_giam (khach_hang_id, phieu_giam_id)
VALUES
(1, 1);

-- Insert into dia_chi
INSERT INTO dia_chi (nguoi_dung_id, so_nha_ten_pho, xa_phuong, quan_huyen, tinh_thanh_pho, ten_nguoi_nhan, ma_buu_chinh)
VALUES
(2, '123 ABC Street', 'Phuong 1', 'Quan Binh Thanh', 'Ho Chi Minh', 'Tran Thi B', '70000');

-- Insert into gio_hang
INSERT INTO gio_hang (nguoi_dung_id, ngay_tao, trang_thai)
VALUES
(2, GETDATE(), 1);

-- Insert into gio_hang_chi_tiet
INSERT INTO gio_hang_chi_tiet (spct_id, gio_hang_id, soluong, ngay_tao, trang_thai)
VALUES
(1, 1, 2, GETDATE(), 1);

-- Insert into lich_su_hoa_don
INSERT INTO lich_su_hoa_don (hoa_don_id, nhan_vien_id, ngay_tao, nguoi_tao, trang_thai)
VALUES
(1, 1, GETDATE(), 'admin', 1);

-- Insert into phuong_thuc_thanh_toan
INSERT INTO phuong_thuc_thanh_toan (hoa_don_id, ten_phuong_thuc)
VALUES
(1, 'Thanh toán khi nhận hàng');

-- Insert into san_pham_yeu_thich
INSERT INTO san_pham_yeu_thich (nguoi_dung_id, spct_id)
VALUES
(2, 1);
select * from hoa_don
go
select * from san_pham
select * from san_pham_chi_tiet
select * from hoa_don_chi_tiet
select * from hinh_anh
select * from nhan_vien
select * from san_pham_yeu_thich
select * from nhan_vien
select * from khach_hang;
select * from nguoi_dung
select * from gio_hang
select * from gio_hang_chi_tiet

DELETE FROM nhan_vien
WHERE id = 6;
DELETE FROM san_pham_yeu_thich
WHERE id BETWEEN 205 AND 211;
-- test kịch bản lấy sản phẩm tranding
SELECT sp.ten_san_pham, SUM(hdct.so_luong) AS total_sold
FROM san_pham sp
JOIN san_pham_chi_tiet spct ON sp.id = spct.san_pham_id
JOIN hoa_don_chi_tiet hdct ON spct.id = hdct.spct_id
GROUP BY sp.ten_san_pham
ORDER BY total_sold DESC;
--- end ----
UPDATE san_pham_chi_tiet
SET gioi_tinh = N'Nữ'
WHERE id = 3;
UPDATE nguoi_dung
SET tai_khoan = 'admin', mat_khau = 'admin'
WHERE id = 1;