function loadRolesCreate(selector) {
    let select = document.getElementById(selector);
    select.innerHTML = "";

    console.log('loadRoles', selector)

    fetch("adminApi/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.role === "ROLE_USER" ? "USER" : role.role === "ROLE_ADMIN" ? "ADMIN" : role.name;
                select.appendChild(option);
            });
        })
}

const selectFields = document.querySelectorAll('.custom-select')

selectFields.forEach(selectField => {
    loadRolesCreate(selectField.getAttribute('id'))
})