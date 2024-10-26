
/**
 * navbar toggle
 */

const overlay = document.querySelector("[data-overlay]");
const navOpenBtn = document.querySelector("[data-nav-open-btn]");
const navbar = document.querySelector("[data-navbar]");
const navCloseBtn = document.querySelector("[data-nav-close-btn]");

const navElems = [overlay, navOpenBtn, navCloseBtn];

for (let i = 0; i < navElems.length; i++) {
  navElems[i].addEventListener("click", function () {
    navbar.classList.toggle("active");
    overlay.classList.toggle("active");
  });
}



/**
 * header & go top btn active on page scroll
 */

const header = document.querySelector("[data-header]");
const goTopBtn = document.querySelector("[data-go-top]");

window.addEventListener("scroll", function () {
  if (window.scrollY >= 80) {
    header.classList.add("active");
    goTopBtn.classList.add("active");
  } else {
    header.classList.remove("active");
    goTopBtn.classList.remove("active");
  }
});

$(document).ready(function () {
  $.ajax({
    url: "/api/user-info",
    method: "GET",
    success: function (data) {
      if (data && data.hoTen) {

        $('#loginItem').hide();
        $('#accountItem').show();
        $('#userName').text(data.hoTen);
      } else {
        $('#loginItem').show();
        $('#accountItem').hide();
      }
    },
    error: function () {
      $('#loginItem').show();
      $('#accountItem').hide();
    }
  });

    if ($("#accountItem").length && $("#accountModal").length) {
      console.log("#accountItem và #accountModal đã tồn tại");
      $("#accountItem a").on("click", function (event) {
        event.preventDefault();
        console.log("Click đã được xử lý");
        $("#accountModal").modal('show');
      });
    } else {
      console.log("#accountItem hoặc #accountModal không tồn tại");
    }

});


document.addEventListener('DOMContentLoaded', function () {
  const filterButtons = document.querySelectorAll('.filter-btn');
  const productContainer = document.getElementById('product-list');

  filterButtons.forEach(button => {
    button.addEventListener('click', function () {
      const brand = this.getAttribute('data-filter');
      loadTopSellingProducts(brand);
    });
  });

  // Hàm để tải sản phẩm bán chạy từ API
  function loadTopSellingProducts(brand) {
    let apiUrl = `/api/san-pham/top-selling?brandName=${brand}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(products => {
          productContainer.innerHTML = ''; // Xóa sản phẩm cũ

          if (products.length === 0) {
            productContainer.innerHTML = '<p>No products available</p>';
          } else {
            products.forEach(product => {
              const productHTML = `
              <li class="product-item">
                <div class="product-card" tabindex="0">
                  <figure class="card-banner">
                    <img src="${product.hinhAnh}" width="312" height="350" loading="lazy" alt="${product.tenSanPham}" class="image-contain">
                    <div class="card-badge">Best Seller</div>
                    <ul class="card-action-list">
                      <li class="card-action-item">
                        <button class="card-action-btn" aria-labelledby="card-label-1">
                          <ion-icon name="cart-outline"></ion-icon>
                        </button>
                        <div class="card-action-tooltip" id="card-label-1">Add to Cart</div>
                      </li>
                      <!-- Các nút khác -->
                    </ul>
                  </figure>
                  <div class="card-content">
                    <div class="card-cat">
                      <a href="#" class="card-cat-link">${product.thuongHieu}</a>
                    </div>
                    <h3 class="h3 card-title">
                      <a href="#">${product.tenSanPham}</a>
                    </h3>
                    <data class="card-price" value="${product.giaTien}">$${product.giaTien}</data>
                  </div>
                </div>
              </li>
            `;
              productContainer.insertAdjacentHTML('beforeend', productHTML);
            });
          }
        })
        .catch(error => console.error('Error fetching products:', error));
  }

  // Tải tất cả sản phẩm bán chạy khi trang tải lần đầu
  loadTopSellingProducts('All');
});










