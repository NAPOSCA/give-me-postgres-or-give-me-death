function initialize() {
	const manyCategoryItemsDivs = document.querySelectorAll(".category-items");
	for (let i = 0; i < manyCategoryItemsDivs.length; i++) {
		const categoryDiv = manyCategoryItemsDivs[i];
		const categorySection = categoryDiv.querySelector(".category");
		const accordionIcons = categorySection.querySelectorAll(".icon.accordion");
		const items = categoryDiv.querySelector(".items");
		categorySection.addEventListener("click", () => {
			toggleVisibility(items);
			accordionIcons.forEach(indicator => {
				toggleClasses(indicator, "collapsed", "expanded");
			});
		});
	}
	const dichotomousProductButtons = document.querySelectorAll(".icon.dichotomous-product");
	for (let i = 0; i < dichotomousProductButtons.length; i++) {
		const dichotomousProductButton = dichotomousProductButtons[i];
		const productId = dichotomousProductButton.parentElement.parentElement.value;
		dichotomousProductButton.addEventListener("click", () => {
			const callback = response => {
				toggleClasses(dichotomousProductButton, "plus", "x");
			};
			if (dichotomousProductButton.classList.contains("plus")) {
				request(callback, "POST", `/cart/products/${productId}`);
			}
			if (dichotomousProductButton.classList.contains("x")) {
				request(callback, "DELETE", `/cart/products/${productId}`);
			}
		});
	}
	const quantifiedProductPlusButtons = document.querySelectorAll(".icon.quantified-product.plus");
	quantifiedProductPlusButtons.forEach(button => {
		const productId = button.parentElement.parentElement.value;
		const quantity = parseInt(button.parentElement.querySelector(".quantity").textContent);
		button.addEventListener("click", () => {
			request(response => {}, "PUT", `/cart/products/${productId}?quantity=${quantity + 1}`);
		});
	});
	const quantifiedProductMinusButtons = document.querySelectorAll(".icon.quantified-product.minus");
	quantifiedProductMinusButtons.forEach(button => {
		const productId = button.parentElement.parentElement.value;
		const quantity = parseInt(button.parentElement.querySelector(".quantity").textContent);
		button.addEventListener("click", () => {
			request(response => {}, "PUT", `/cart/products/${productId}?quantity=${quantity - 1}`)
		});
	});
}

function toggleClasses(element) {
	for (let i = 1; i < arguments.length; i++) {
		element.classList.toggle(arguments[i]);
	}
}

function toggleVisibility(element) {
	toggleClasses(element, "hidden", "visible");
}

const request = (callback, method, url) => {
	const xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if (xhr.readyState === 4 && xhr.status === 200) {
			callback(xhr.response);
		}
	};
	xhr.open(method, url, true);
	const token = getMetaContent("name", "_csrf");
	const header = getMetaContent("name", "_csrf_header");
	xhr.setRequestHeader(header, token);
	xhr.send();
};

const getMetaContent = (property, name) => document.head.querySelector("[" + property + "=" + name + "]").content;