function initializeUserForm() {
	const familySizeMenu = document.querySelector(".familySizeMenu");
	const schoolKidsMenu = document.querySelector(".schoolKidsMenu");
	const schoolKidsOptions = document.querySelectorAll(".schoolKidsMenu option")

	familySizeMenu.addEventListener("change", () => {
		const currentFamilySize = familySizeMenu.value;
		for (let i = 1; i < schoolKidsOptions.length; i++) {
			const currentOption = schoolKidsOptions[i];
			currentOption.classList.remove("hidden");
			if (currentOption.value > currentFamilySize)  {
				currentOption.classList.add("hidden");				
			}
		}
	})


	document.getElementById('prime-phone').addEventListener('input', function (e) {
		var x = e.target.value.replace(/\D/g, '').match(/(\d{0,3})(\d{0,3})(\d{0,4})/);
		e.target.value = !x[2] ? x[1] : '(' + x[1] + ') ' + x[2] + (x[3] ? '-' + x[3] : '');
	  });

	  document.getElementById('sec-phone').addEventListener('input', function (e) {
		var x = e.target.value.replace(/\D/g, '').match(/(\d{0,3})(\d{0,3})(\d{0,4})/);
		e.target.value = !x[2] ? x[1] : '(' + x[1] + ') ' + x[2] + (x[3] ? '-' + x[3] : '');
	  });
}
