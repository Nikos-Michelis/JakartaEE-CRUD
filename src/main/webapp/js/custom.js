function initializePopup() {
  const formPopup = document.querySelector(".form-popup");
  const showPopupBtnAdd = document.querySelector(".add-button");
  const closeButton = formPopup.querySelector(".close-btn");

  // Show popup
  if (showPopupBtnAdd) {
    showPopupBtnAdd.addEventListener("click", () => {
      document.body.classList.toggle("show-popup");
    });
  }

  // close the popup
  if (closeButton) {
    closeButton.addEventListener('click', function () {
      document.body.classList.remove("show-popup");
    });
  }
}

document.addEventListener("DOMContentLoaded", function () {
  const autoPopupSection = document.querySelector(".auto-popup");

  if (autoPopupSection) {
    document.body.classList.add('show-popup');
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
    initializePopup();
  }
  const errorsExist = document.querySelector(".error-message");
  if (errorsExist) {
    document.body.classList.add("show-popup");
  }
  const closeButton = document.querySelector(".close-btn");
  if (closeButton) {
    closeButton.addEventListener('click', function () {
      document.body.classList.remove("show-popup");
    });
  }
});
