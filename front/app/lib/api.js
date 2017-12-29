import axios from 'axios';
import {
    BASE_URL,
    CONTENT_TYPES,
    USER_ENDPOINTS,
    MAIN_ENDPOINTS,
    IMAGE_ENDPOINTS,
    ADMIN_ENDPOINTS
} from '../_consts/api'

class API {
    constructor() {
        this.request = axios.create({
            baseURL: BASE_URL,
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': CONTENT_TYPES.JSON
            },
            withCredentials: true
        });
    }

    login(user) {
        this.request.post(USER_ENDPOINTS.LOGIN, user).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    logout() {
        this.request.get(USER_ENDPOINTS.LOGOUT).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    register(user) {
        this.request.post(USER_ENDPOINTS.REGISTRATION, user).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    currentUser() {
        this.request.get(USER_ENDPOINTS.USERS).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    updateUser(id, user) {
        this.request.put(USER_ENDPOINTS.USERS + `/${id}`, user).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    deleteUser(id) {
        this.request.delete(USER_ENDPOINTS + `/${id}`).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    getAllUsers_Admin() {
        this.request.get(ADMIN_ENDPOINTS.USERS).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    updateUser_Admin(id, user) {
        this.request.put(ADMIN_ENDPOINTS.USERS + `/${id}`, user).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    deleteUser_Admin(id) {
        this.request.delete(ADMIN_ENDPOINTS.USERS + `/${id}`).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    getMenu() {
        this.request.get(MAIN_ENDPOINTS.MENU).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    getImage(name) {
        this.request.get(IMAGE_ENDPOINTS.GET + `/${name}`).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    sendImage(image) {
        this.request.post(IMAGE_ENDPOINTS.SEND, image).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    addMeal(meal) {
        this.request.post(ADMIN_ENDPOINTS.MEALS, meal).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    updateMeal(id, meal) {
        this.request.put(ADMIN_ENDPOINTS.MEALS + `/${id}`, meal).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    deleteMeal(id) {
        this.request.delete(ADMIN_ENDPOINTS.MEALS + `/${id}`).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }
}

export default API;