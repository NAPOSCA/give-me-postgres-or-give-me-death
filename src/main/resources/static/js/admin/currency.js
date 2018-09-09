function initialize() {
	const currencyId = document.querySelector("#id").textContent;
	const detailsForm = document.querySelector(".category-details");
	const updateButton = document.querySelector("#update");
	updateButton.addEventListener("click", () => {
		const name = detailsForm.querySelector("input.name").value;
		const unit = detailsForm.querySelector("input.unit").value;
		const allowanceMap = detailsForm.querySelector("input.allowanceMap");
		request(response => {
			document.location.reload(true);
		}, "PUT", `/admin/currency/${currencyId}?name=${name}&unit=${unit}&allowanceMap=${allowanceMap}`);
	});
	const deleteButton = document.querySelector("#delete");
	deleteButton.addEventListener("click", () => {
		const confirmation = confirm("Are you sure that you want to delete this currency and all products inside it?");
		if (confirmation) {
			request(response => {
				document.location.reload(true);
			}, "DELETE", `/admin/currency/${currencyId}`);
		}
	});
}