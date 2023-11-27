function initializePopup() {
  const formPopup = document.querySelector(".form-popup");
  const showPopupBtnAdd = document.querySelector(".add-button");
  const closeButton = formPopup.querySelector(".close-btn");

  // Show login popup on button click
  if (showPopupBtnAdd) {
    showPopupBtnAdd.addEventListener("click", () => {
      document.body.classList.toggle("show-popup");
    });
  }

  // Optional: Add an event listener to close the popup when clicking on the close button
  if (closeButton) {
    closeButton.addEventListener('click', function () {
      document.body.classList.remove("show-popup");
    });
  }
}

document.addEventListener("DOMContentLoaded", function () {
  const autoPopupSection = document.querySelector(".auto-popup");

  if (autoPopupSection) {
    // Automatically show the form-popup for pages with the specific class
    document.body.classList.add('show-popup');
    // Optional: Add an event listener to handle file input change
    const fileInput = autoPopupSection.querySelector('#fileImage');
    const imageLabel = autoPopupSection.querySelector('.image-upload-label');
    const previewImage = imageLabel.querySelector('img');
    fileInput.addEventListener('change', handleFileSelect);

    function handleFileSelect(event) {
      console.log("File input changed");
      const selectedFile = event.target.files[0];

      if (selectedFile) {
        const reader = new FileReader();

        reader.onload = function (e) {
          previewImage.src = e.target.result;
        };

        reader.readAsDataURL(selectedFile);
      }
    }
  } else {
    // Initialize popup interactions only if the add-button is present on the page
    initializePopup();
  }

  // Check if there are errors and show the popup if errors exist
  const errorsExist = document.querySelector(".error-message");
  if (errorsExist) {
    document.body.classList.add("show-popup");
  }

  // Optional: Add an event listener to close the popup
  const closeButton = document.querySelector(".close-btn");
  if (closeButton) {
    closeButton.addEventListener('click', function () {
      document.body.classList.remove("show-popup");
    });
  }
});
