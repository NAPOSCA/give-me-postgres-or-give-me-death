function initialize() {
	const categoryId = document.querySelector("#id").textContent;
	const detailsForm = document.querySelector(".category-details");
	const updateButton = document.querySelector("#update");
	updateButton.addEventListener("click", () => {
		const name = detailsForm.querySelector("input.name").value;
		const schoolAgeChildrenRequired = detailsForm.querySelector("input.schoolAgeChildrenRequired").checked;
		request(response => {
			document.location.reload(true);
		}, "PUT", `/admin/category/${categoryId}?name=${name}&schoolAgeChildrenRequired=${schoolAgeChildrenRequired}`);
	});
	const deleteButton = document.querySelector("#delete");
	deleteButton.addEventListener("click", () => {
		const confirmation = confirm("Are you sure you want to delete this category and all products inside it?");
		if (confirmation) {
			request(response => {
				document.location.reload(true);
			}, "DELETE", `/admin/category/${categoryId}`);
		}
	});
}