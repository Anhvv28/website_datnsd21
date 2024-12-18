package com.fpt.tool;

import com.fpt.niceshoes.entity.*;
import com.fpt.niceshoes.infrastructure.constant.AccountRoles;
import com.fpt.niceshoes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.fpt.niceshoes.repository")
public class DBGenerator implements CommandLineRunner {

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private IBillDetailRepository billDetailRepository;
    @Autowired
    private IBillHistoryRepository billHistoryRepository;
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private IBrandRepository brandRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IColorRepository colorRepository;
    @Autowired
    private IImagesRepository imagesRepository;
    @Autowired
    private IPaymentMethodRepository paymentMethodRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IShoeDetailRepository shoeDetailRepository;
    @Autowired
    private IShoeRepository shoeRepository;
    @Autowired
    private ISizeRepository sizeRepository;
    @Autowired
    private ISoleRepository soleRepository;
    @Autowired
    private IVoucherRepository voucherRepository;
    @Autowired
    private IAccountVoucherRepository accountVoucherRepository;
    @Autowired
    private IPromotionRepository promotionRepository;
    @Autowired
    IPromotionDetailRepository promotionDetailRepository;

    @Override
    public void run(String... args) throws Exception {
        // Category
        Category category1 = new Category();
        category1.setName("Chạy bộ");
        category1.setId(categoryRepository.save(category1).getId());

        Category category2 = new Category();
        category2.setName("Bóng đá");
        category2.setId(categoryRepository.save(category2).getId());

        // Brand
        Brand brand1 = new Brand();
        brand1.setName("Nike");
        brand1.setId(brandRepository.save(brand1).getId());

        Brand brand2 = new Brand();
        brand2.setName("Adidas");
        brand2.setId(brandRepository.save(brand2).getId());

        // Shoe
        Shoe shoe1 = new Shoe();
        shoe1.setName("Nike Air Max");
        shoe1.setDescription("Đây là mô tả");
        shoe1.setBrand(brand1);
        shoe1.setCategory(category1);
        shoe1.setId(shoeRepository.save(shoe1).getId());

        Shoe shoe2 = new Shoe();
        shoe2.setName("Adidas Superstar");
        shoe2.setDescription("hehehehehe");
        shoe2.setBrand(brand2);
        shoe2.setCategory(category2);
        shoe2.setId(shoeRepository.save(shoe2).getId());

        // Size
        Size size36 = new Size();
        size36.setName("36");
        size36.setId(sizeRepository.save(size36).getId());

        Size size37 = new Size();
        size37.setName("37");
        size37.setId(sizeRepository.save(size37).getId());

        Size size39 = new Size();
        size39.setName("39");
        size39.setId(sizeRepository.save(size39).getId());

        Size size40 = new Size();
        size40.setName("40");
        size40.setId(sizeRepository.save(size40).getId());

        Size size41 = new Size();
        size41.setName("41");
        size41.setId(sizeRepository.save(size41).getId());

        // Color
        Color colorRed = new Color();
        colorRed.setName("Đỏ");
        colorRed.setId(colorRepository.save(colorRed).getId());

        Color colorYellow = new Color();
        colorYellow.setName("Vàng");
        colorYellow.setId(colorRepository.save(colorYellow).getId());

        Color colorBlue = new Color();
        colorBlue.setName("Xanh");
        colorBlue.setId(colorRepository.save(colorBlue).getId());

        Color colorBlack = new Color();
        colorBlack.setName("Đen");
        colorBlack.setId(colorRepository.save(colorBlack).getId());

        Color colorWhite = new Color();
        colorWhite.setName("Trắng");
        colorWhite.setId(colorRepository.save(colorWhite).getId());
        // Sole
        Sole sole1 = new Sole();
        sole1.setName("Cao su");
        sole1.setId(soleRepository.save(sole1).getId());

        Sole sole2 = new Sole();
        sole2.setName("Đinh");
        sole2.setId(soleRepository.save(sole2).getId());

        Sole sole3 = new Sole();
        sole3.setName("Đặc");
        sole3.setId(soleRepository.save(sole3).getId());

        // Cart
        // CartDetail

        // Role
        Role nhanVien = new Role();
        nhanVien.setName("Nhân viên");
        nhanVien.setId(roleRepository.save(nhanVien).getId());

        Role khachHang = new Role();
        khachHang.setName("Khách hàng");
        khachHang.setId(roleRepository.save(khachHang).getId());

        // Account
        Account nhanVien1 = new Account();
        nhanVien1.setName("Vu Viet Anh");
        nhanVien1.setBirthday(new Date(2001, 03, 03));
        nhanVien1.setAvatar("");
        nhanVien1.setCccd("033201005434");
        nhanVien1.setEmail("anhvv28@fptcloud.com");
        nhanVien1.setAccountRoles(AccountRoles.ROLE_ADMIN);
        nhanVien1.setGender("Nam");
        nhanVien1.setPassword(new BCryptPasswordEncoder().encode("admin"));
        nhanVien1.setPhoneNumber("0965699901");
        nhanVien1.setUsername("anhvv28");
        nhanVien1.setRole(nhanVien);
        nhanVien1.setId(accountRepository.save(nhanVien1).getId());

        Account nhanVien2 = new Account();
        nhanVien2.setName("Lo Thi B");
        nhanVien2.setBirthday(new Date(03, 5, 21));
        nhanVien2.setAvatar("");
        nhanVien2.setCccd("001203030509");
        nhanVien2.setEmail("lob@gmail.com");
        nhanVien2.setAccountRoles(AccountRoles.ROLE_EMLOYEE);
        nhanVien2.setGender("Nữ");
        nhanVien2.setPassword(new BCryptPasswordEncoder().encode("12345678"));
        nhanVien2.setPhoneNumber("0395080515");
        nhanVien2.setUsername("lob");
        nhanVien2.setRole(nhanVien);
        nhanVien2.setId(accountRepository.save(nhanVien2).getId());

        Account khachHang1 = new Account();
        khachHang1.setName("Hoang Van De");
        khachHang1.setBirthday(new Date(98, 8, 11));
        khachHang1.setAvatar("");
        khachHang1.setCccd("1234567890");
        khachHang1.setEmail("de8@gmail.com");
        khachHang1.setGender("Nam");
        khachHang1.setAccountRoles(AccountRoles.ROLE_USER);
        khachHang1.setPassword("de123456@");
        khachHang1.setPhoneNumber("0989904786");
        khachHang1.setUsername("hvde");
        khachHang1.setRole(khachHang);
        khachHang1.setId(accountRepository.save(khachHang1).getId());

        Account khachHang2 = new Account();
        khachHang2.setName("pham quang chien");
        khachHang2.setBirthday(new Date(03, 7, 30));
        khachHang2.setAvatar("");
        khachHang2.setCccd("5242851929");
        khachHang2.setEmail("phamchien203@gmail.com");
        khachHang2.setGender("Nam");
        khachHang2.setAccountRoles(AccountRoles.ROLE_USER);
        khachHang2.setPassword("chien@");
        khachHang2.setPhoneNumber("0354999999");
        khachHang2.setUsername("phamquangchien");
        khachHang2.setRole(khachHang);
        khachHang2.setId(accountRepository.save(khachHang2).getId());

        // Address
        Address address1 = new Address();
        address1.setName("ngoc tinh");
        address1.setSpecificAddress("Số 69");
        address1.setPhoneNumber("0965699901");
        address1.setDistrict("1827");
        address1.setProvince("268");
        address1.setWard("220807");
        address1.setAccount(nhanVien1);
        address1.setId(addressRepository.save(address1).getId());

        Address address2 = new Address();
        address2.setName("Lang Ha");
        address2.setSpecificAddress("Số 5");
        address2.setPhoneNumber("0387888888");
        address2.setDistrict("2157");
        address2.setProvince("267");
        address2.setWard("800046");
        address2.setAccount(khachHang1);
        address2.setId(addressRepository.save(address2).getId());

        Address address3 = new Address();
        address3.setName("Tay Ho Tay");
        address3.setSpecificAddress("Số 6");
        address3.setPhoneNumber("0387887777");
        address3.setDistrict("3440");
        address3.setProvince("201");
        address3.setWard("907557");
        address3.setAccount(khachHang1);
        address3.setId(addressRepository.save(address3).getId());

        Address address4 = new Address();
        address4.setName("Pham Hung");
        address4.setSpecificAddress("Số 7");
        address4.setPhoneNumber("0387888999");
        address4.setDistrict("2255");
        address4.setProvince("266");
        address4.setWard("141208");
        address4.setAccount(khachHang2);
        address4.setId(addressRepository.save(address4).getId());

        Address address5 = new Address();
        address5.setName("Co loa");
        address5.setSpecificAddress("Số 8");
        address5.setPhoneNumber("0387880000");
        address5.setDistrict("2046");
        address5.setProvince("268");
        address5.setWard("220909");
        address5.setAccount(khachHang2);
        address5.setId(addressRepository.save(address5).getId());
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }
}
