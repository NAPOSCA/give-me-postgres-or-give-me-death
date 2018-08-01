function initialize() {
	const categoryId = document.querySelector("#id").textContent;
	const detailsForm = document.querySelector(".category-details");
	const updateButton = document.querySelector("#update");
	updateButton.addEventListener("click", () => {
		const name = detailsForm.querySelector("#name").value;
		request(response => {
			document.location.reload(true);
		}, "PUT", `/admin/category/${categoryId}?name=${name}`);
	});
	const deleteButton = document.querySelector("#delete");
	deleteButton.addEventListener("click", () => {
		const confirmation = confirm("Are you sure you want to delete this category?");
		if (confirmation) {
			request(response => {
				document.location.reload(true);
			}, "DELETE", `/admin/category/${categoryId}`);
		}
	});
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