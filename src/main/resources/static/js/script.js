
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












