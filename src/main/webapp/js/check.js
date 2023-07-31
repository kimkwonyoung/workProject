const form = document.querySelector('form');
const userIdInput = document.getElementById('userid');
const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('password2');
const usernameInput = document.getElementById('username');
const phoneNumberInput = document.getElementById('tel');

// 이벤트 리스너를 추가합니다.
if(userIdInput) {
	userIdInput.addEventListener('input', validateUserId);
}

passwordInput.addEventListener('input', validatePassword);
confirmPasswordInput.addEventListener('input', validateConfirmPassword);
usernameInput.addEventListener('input', validateUsername);
phoneNumberInput.addEventListener('input', validatePhoneNumber);

function validateUserId() {
	const userId = userIdInput.value;
	const errorElement = userIdInput.parentElement.querySelector('.error-message');
	const isValid = /^[a-zA-Z0-9]*$/.test(userId);

	if (!isValid) {
		if (!errorElement) {
			displayErrorMessage(userIdInput, '영문 숫자만 입력하세요.');
		}
		userIdInput.value = userId.replace(/[^a-zA-Z0-9]/g, '');
	} else {
		if (errorElement) {
			errorElement.remove();
		}
	}
}

function validatePassword() {
	const password = passwordInput.value;
	const errorElement = passwordInput.parentElement.querySelector('.error-message');
	const isValid = /^[A-Za-z0-9!@#$%^&*]{6,}$/.test(password);

	if (!isValid) {
		if (!errorElement) {
			displayErrorMessage(passwordInput, '6자리이상 숫자, 영문, 특수문자(!@#$%^&*)만 입력하세요.');
		}
	} else {
		if (errorElement) {
			errorElement.remove();
		}
	}
}

function validateConfirmPassword() {
	const password = passwordInput.value;
	const confirmPassword = confirmPasswordInput.value;
	const errorElement = confirmPasswordInput.parentElement.querySelector('.error-message');
	const isValid = password === confirmPassword;

	if (!isValid) {
		if (!errorElement) {
			displayErrorMessage(confirmPasswordInput, '비밀번호가 일치하지 않습니다.');
		}
	} else {
		if (errorElement) {
			errorElement.remove();
		}
	}
}

function validateUsername() {
	const username = usernameInput.value;
	const errorElement = usernameInput.parentElement.querySelector('.error-message');
	const isValid = /^[a-zA-Zㄱ-ㅎ가-힣]*$/.test(username);

	if (!isValid) {
		if (!errorElement) {
			displayErrorMessage(usernameInput, '한글 또는 영문만 입력하세요.');
		}
		usernameInput.value = username.replace(/[^a-zA-Zㄱ-ㅎ가-힣]/g, '');
	} else {
		if (errorElement) {
			errorElement.remove();
		}
	}
}


function validatePhoneNumber() {
	const phoneNumber = phoneNumberInput.value;
	const errorElement = phoneNumberInput.parentElement.querySelector('.error-message');
	const isValid = /^[0-9]*$/.test(phoneNumber);

	if (!isValid) {
		if (!errorElement) {
			displayErrorMessage(phoneNumberInput, '숫자만 입력하세요.');
		}
		phoneNumberInput.value = phoneNumber.replace(/[^0-9]/g, '');
	} else {
		if (errorElement) {
			errorElement.remove();
		}
		if (phoneNumber.length === 11) {
			const formattedValue = phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			phoneNumberInput.value = formattedValue;
		}
	}
}

function displayErrorMessage(inputElement, message) {
	const errorElement = inputElement.parentElement.querySelector('.error-message');

	if (errorElement) {
		errorElement.textContent = message;
	} else {
		const newErrorElement = document.createElement('p');
		newErrorElement.classList.add('error-message');
		newErrorElement.textContent = message;
		newErrorElement.style.color = 'red';
		inputElement.parentElement.appendChild(newErrorElement);
	}
}