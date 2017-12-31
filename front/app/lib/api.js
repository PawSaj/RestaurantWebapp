import axios from 'axios';
import {
    BASE_URL,
    CONTENT_TYPES,
    USER_ENDPOINTS,
    MAIN_ENDPOINTS,
    IMAGE_ENDPOINTS,
    ADMIN_ENDPOINTS
} from '../_consts/api/api'

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
        return this.request.post(USER_ENDPOINTS.LOGIN, user);
    }

    logout() {
        return this.request.get(USER_ENDPOINTS.LOGOUT);
    }

    register(user) {
        return this.request.post(USER_ENDPOINTS.REGISTRATION, user);
    }

    currentUser() {
        this.request.get(USER_ENDPOINTS.USERS).then((response) => {
            let {data} = response;
            console.log(data);
        }).catch((error) => {
            console.log(error);
        })
    }

    updateUser(id, user) {
        return this.request.put(USER_ENDPOINTS.USERS + `/${id}`, user);
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
        return this.request.get(MAIN_ENDPOINTS.MENU);
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