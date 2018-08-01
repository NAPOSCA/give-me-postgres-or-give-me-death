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

const request = (callback, method, url) => {
	const xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if (xhr.readyState === 4 && xhr.status === 200) {
			const response = JSON.parse(xhr.response);
			callback(response);
		}
	};
	xhr.open(method, url, true);
	const token = getMetaContent("name", "_csrf");
	const header = getMetaContent("name", "_csrf_header");
	xhr.setRequestHeader(header, token);
	xhr.send();
};

const getMetaContent = (property, name) => document.head.querySelector("[" + property + "=" + name + "]").content;