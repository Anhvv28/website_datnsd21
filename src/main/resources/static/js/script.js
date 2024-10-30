
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
      // console.log("#accountItem và #accountModal đã tồn tại");
      $("#accountItem a").on("click", function (event) {
        event.preventDefault();
        $("#accountModal").modal('show');
      });
    } else {
      // console.log("#accountItem hoặc #accountModal không tồn tại");
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

  // Function to check if the user is logged in
  function checkLoginStatus(callback) {
    $.ajax({
      url: "/api/user-info",
      method: "GET",
      success: function (data) {
        if (data && data.hoTen) {
          callback(true); // User is logged in
        } else {
          callback(false); // User is not logged in
        }
      },
      error: function () {
        callback(false); // User is not logged in
      }
    });
  }

  // Function to fetch user's favorite products
  function fetchUserFavorites(callback) {
    fetch('/api/favorites/user', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include'
    })
        .then(response => response.json())
        .then(data => {
          if (Array.isArray(data)) {
            callback(data); // List of favorite product IDs
          } else {
            callback([]);
          }
        })
        .catch(error => {
          console.error('Error fetching user favorites:', error);
          callback([]);
        });
  }

  // Load top-selling products and mark favorites
  function loadTopSellingProducts(brand) {
    fetchUserFavorites(favoriteProductIds => {
      let apiUrl = `/api/san-pham/top-selling?brandName=${brand}`;
      fetch(apiUrl)
          .then(response => response.json())
          .then(products => {
            productContainer.innerHTML = '';

            if (products.length === 0) {
              productContainer.innerHTML = '<p>Không có sản phẩm nào.</p>';
            } else {
              products.forEach(product => {
                const isFavorited = favoriteProductIds.includes(product.id);
                const favoriteIcon = isFavorited ? 'heart' : 'heart-outline';
                const favoritedClass = isFavorited ? 'favorited' : '';

                const productHTML = `
              <li class="product-item">
                <div class="product-card" tabindex="0">
                  <figure class="card-banner">
                    <img src="${product.hinhAnh}" width="312" height="350" loading="lazy" alt="${product.tenSanPham}" class="image-contain">
                    <div class="card-badge">Best Seller</div>
                    <ul class="card-action-list">
                      <li class="card-action-item">
                        <button type="button" class="card-action-btn add-to-cart-btn" data-product-id="${product.id}" data-action="add-to-cart" aria-labelledby="card-label-1">
                          <ion-icon name="cart-outline"></ion-icon>
                        </button>
                        <div class="card-action-tooltip" id="card-label-1">Thêm Giỏ Hàng</div>
                      </li>
                      <li class="card-action-item">
                        <button type="button" class="card-action-btn favorite-btn ${favoritedClass}" data-product-id="${product.id}" data-action="toggle-favorite" aria-labelledby="card-label-2">
                          <ion-icon name="${favoriteIcon}"></ion-icon>
                        </button>
                        <div class="card-action-tooltip" id="card-label-2">Yêu Thích</div>
                      </li>
                      <li class="card-action-item">
                        <button type="button" class="card-action-btn view-details-btn" data-product-id="${product.id}" data-action="view-details" aria-labelledby="card-label-3">
                          <ion-icon name="eye-outline"></ion-icon>
                        </button>
                        <div class="card-action-tooltip" id="card-label-3">Xem Chi Tiết</div>
                      </li>
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
          .catch(error => console.error('Lỗi khi tải sản phẩm:', error));
    });
  }


  // Handle actions on buttons in product cards
  productContainer.addEventListener('click', function (event) {
    const button = event.target.closest('.card-action-btn');
    if (!button) return;

    const productId = button.getAttribute('data-product-id');
    const action = button.getAttribute('data-action');

    if (!productId) {
      console.error('ID sản phẩm không hợp lệ.');
      return;
    }

    // Check if the user is logged in before performing the action
    checkLoginStatus(function (isLoggedIn) {
      if (!isLoggedIn) {
        alert("Bạn cần đăng nhập để thực hiện hành động này.");
        window.location.href = "/login-form";
        return;
      }

      if (action === 'add-to-cart') {
        addToCart(productId);
      } else if (action === 'toggle-favorite') {
        toggleFavorite(button, productId);
      } else if (action === 'view-details') {
        viewProductDetails(productId);
      }
    });
  });

  // Function to add product to cart
  function addToCart(productId) {
    const apiUrl = '/api/gio-hang/them';
    const requestData = {
      productId: parseInt(productId, 10)
    };

    fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestData),
      credentials: 'include'
    })
        .then(async response => {
          const data = await response.json();
          if (!response.ok) {
            throw new Error(data.error || 'Lỗi không xác định');
          }
          displaySuccessMessage(data.message);
          updateCartUI();
        })
        .catch(error => handleAddToCartError(error));
  }

  // Function to toggle favorite status
  function toggleFavorite(button, sanPhamChiTietId) {
    const icon = button.querySelector('ion-icon');
    const isFavorited = icon.getAttribute('name') === 'heart';
    const url = isFavorited ? `/api/favorites/remove?sanPhamChiTietId=${sanPhamChiTietId}` : `/api/favorites/add?sanPhamChiTietId=${sanPhamChiTietId}`;

    fetch(url, {
      method: isFavorited ? 'DELETE' : 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    })
        .then(response => {
          if (response.ok) {
            if (isFavorited) {
              icon.setAttribute('name', 'heart-outline');
              button.classList.remove('favorited');
              showToast('Đã bỏ yêu thích');
            } else {
              icon.setAttribute('name', 'heart');
              button.classList.add('favorited');
              showToast('Đã thêm vào yêu thích');
            }
          } else {
            return response.json().then(err => {
              throw new Error(err.message || 'Lỗi khi cập nhật trạng thái yêu thích');
            });
          }
        })
        .catch(error => {
          console.error('Lỗi:', error);
          showToast('Có lỗi xảy ra khi thực hiện yêu thích sản phẩm');
        });
  }

// Function to view product details
  function viewProductDetails(sanPhamChiTietId) {
    window.location.href = `/product-details?sanPhamChiTietId=${sanPhamChiTietId}`;
  }

// Function to display success message
  function displaySuccessMessage(message) {
    alert(message);
  }

// Function to update cart UI
  function updateCartUI() {
    // Refresh cart count or update cart dropdown, etc.
  }

// Function to handle add-to-cart errors
  function handleAddToCartError(error) {
    console.error('Lỗi khi thêm sản phẩm vào giỏ hàng:', error);
    alert(error.message);
  }

// Function to show toast notification
  function showToast(message) {
    const toast = document.createElement('div');
    toast.classList.add('toast');
    toast.textContent = message;
    document.body.appendChild(toast);
    setTimeout(() => {
      toast.remove();
    }, 3000);
  }

  // Load top-selling products on page load
  loadTopSellingProducts('All');
});








document.addEventListener('DOMContentLoaded', function () {
  // Elements for the chatbot interface
  const chatbotIcon = document.getElementById('chatbot-icon');
  const chatbotBox = document.getElementById('chatbot-box');
  const chatbotMessages = document.getElementById('chatbot-messages');
  const chatbotInput = document.getElementById('chatbot-input');
  const chatbotSendButton = document.getElementById('chatbot-send-btn');

  // Hide the chatbot box by default
  chatbotBox.style.display = 'none';

  // Show the chatbot box when the icon is clicked
  chatbotIcon.addEventListener('click', () => {
    if (chatbotBox.style.display === 'none') {
      chatbotBox.style.display = 'flex';
    } else {
      chatbotBox.style.display = 'none';
    }
  });

  // Function to send message to chatbot
  function sendMessageToChatbot(userMessage) {
    // Create a user message element
    const userMessageElement = document.createElement('div');
    userMessageElement.classList.add('user-message');
    userMessageElement.textContent = userMessage;
    chatbotMessages.appendChild(userMessageElement);

    // Scroll to the latest message
    chatbotMessages.scrollTop = chatbotMessages.scrollHeight;

    // Send the message to the backend API
    fetch('/api/chatbot', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ message: userMessage })
    })
        .then(response => response.json())
        .then(data => {
          // Create a bot message element for the reply
          const botMessageElement = document.createElement('div');
          botMessageElement.classList.add('bot-message');
          botMessageElement.textContent = data.reply; // Using 'reply' field returned by the backend
          chatbotMessages.appendChild(botMessageElement);

          // Scroll to the latest message
          chatbotMessages.scrollTop = chatbotMessages.scrollHeight;
        })
        .catch(error => {
          console.error('Error:', error);
          // Display an error message to the user
          const errorMessageElement = document.createElement('div');
          errorMessageElement.classList.add('bot-message');
          errorMessageElement.textContent = 'Sorry, something went wrong.';
          chatbotMessages.appendChild(errorMessageElement);
          chatbotMessages.scrollTop = chatbotMessages.scrollHeight;
        });
  }

  // Handle the send button click event
  chatbotSendButton.addEventListener('click', () => {
    const userMessage = chatbotInput.value.trim();
    if (userMessage !== '') {
      sendMessageToChatbot(userMessage);
      chatbotInput.value = ''; // Clear input field after sending the message
    }
  });

  // Handle enter key press in the input field to send a message
  chatbotInput.addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
      chatbotSendButton.click();
    }
  });
});




















