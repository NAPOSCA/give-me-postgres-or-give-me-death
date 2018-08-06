function initialize() {
    const manyCategoryItemsDivs = document.querySelectorAll(".category-items");
	for (let i = 0; i < manyCategoryItemsDivs.length; i++) {
		const categoryDiv = manyCategoryItemsDivs[i];
		const categorySection = categoryDiv.querySelector(".category");
		const indicators = categorySection.querySelectorAll(".icon.big-x");
		const items = categoryDiv.querySelector(".items");
		categorySection.addEventListener("click", () => {
			toggleVisibility(items);
			indicators.forEach(indicator => {
				toggleClasses(indicator, "big-x", "accordion");
			});
		});
	}
}

function toggleClasses(element) {
    for(let i = 1; i < arguments.length; i++) {
        element.classList.toggle(arguments[i]);
    }
}

function toggleVisibility(element) {
    toggleClasses(element, "hidden", "visible");
}