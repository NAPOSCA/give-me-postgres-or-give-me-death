function initialize() {
	const categoryId = document.querySelector("#id");
	const detailsForm = document.querySelector(".category-details");
	const updateButton = document.querySelector("#update");
	const deleteButton = document.querySelector("#delete");
	deleteButton.addEventListener("click", () => {
		request(response => {
			document.location.reload(true);
		}, "DELETE", `/admin/category/${categoryId}`);
	});
}

function request(callback, method, path) {
	const xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if (xhr.readyState == 4 && xhr.status == 200) {
			const response = JSON.parse(xhr.response);
			callback(response);
		}
	};
	xhr.open(method, path, true);
	xhr.send();
}
