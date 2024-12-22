'use strict';

let deleteForm = document.forms["formDelete"]

async function deleteModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#delete'));
    await openAndFillInTheModal(deleteForm, modal, id);
    switch (deleteForm.roles.value) {
        case '1':
            deleteForm.roles.value = 'ROLE_ADMIN';
            break;
        case '2':
            deleteForm.roles.value = 'ROLE_USER';
            break;
    }
    deleteUser()
}

function deleteUser() {
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch("adminApi/user/" + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            $('#closeDelete').click();
            getTableUser();
        });
    });
}