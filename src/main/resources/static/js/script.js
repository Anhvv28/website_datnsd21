'use strict';

  // Get the modal
  var modal = document.getElementById("loginModal");

  // Get the icon that opens the modal
  var loginIcon = document.getElementById("loginIcon");

  // Get the <span> element that closes the modal
    var closeBtn = document.getElementsByClassName("close")[0];

    // When the user clicks the icon, open the modal
    loginIcon.onclick = function() {
    modal.style.display = "flex"; // Show modal and center it using flexbox
  }

  // When the user clicks on <span> (x), close the modal
    closeBtn.onclick = function() {
      modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
      if (event.target == modal) {
      modal.style.display = "none";
    }
    }

    // Function to open the modal when clicking on "Login / Register"
    function openLoginModal() {
      modal.style.display = "flex";
    }

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