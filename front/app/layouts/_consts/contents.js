import {MAIN_PATHS, RESERVATIONS_PATHS, ADMIN_PATHS, MANAGER_PATHS} from '../../_consts/paths';

/* components main */
import Home from '../../components/main/home';
import Menu from '../../components/main/menu';
import Reservation from '../../components/main/reservations/reservation';
import Profile from '../../components/main/profile';
import BookLocal from '../../components/main/reservations/bookLocal';
import BookTable from '../../components/main/reservations/bookTable';

/* components admin */
import AdminPanel from '../../components/admin/panel';
import AdminMenu from '../../components/admin/menu/menu';
import AdminReservation from '../../components/admin/reservations/reservations';

/* components manager */
import ManagerPanel from '../../components/manager/panel';

export const MAIN_CONTENT = {
    urlToRedirect: MAIN_PATHS.HOME,
    routes: [
        {
            path: MAIN_PATHS.HOME,
            component: Home
        }, {
            path: MAIN_PATHS.RESERVATIONS,
            component: Reservation
        }, {
            path: MAIN_PATHS.RESERVATIONS + RESERVATIONS_PATHS.LOCAL,
            component: BookLocal
        }, {
            path: MAIN_PATHS.RESERVATIONS + RESERVATIONS_PATHS.TABLE,
            component: BookTable
        }, {
            path: MAIN_PATHS.MENU,
            component: Menu
        }, {
            path: MAIN_PATHS.PROFILE,
            component: Profile
        }
    ]
};

export const ADMIN_CONTENT = {
    urlToRedirect: ADMIN_PATHS.HOME,
    routes: [
        {
            path: ADMIN_PATHS.HOME,
            component: AdminPanel
        }, {
            path: ADMIN_PATHS.RESERVATIONS,
            component: AdminReservation
        }, {
            path: ADMIN_PATHS.MENU,
            component: AdminMenu
        }, {
            path: ADMIN_PATHS.TABLES,
            component: null
        }, {
            path: ADMIN_PATHS.USERS,
            component: null
        }
    ]
};


export const MANAGER_CONTENT = {
    urlToRedirect: MANAGER_PATHS.HOME,
    routes: [
        {
            path: MANAGER_PATHS.HOME,
            component: ManagerPanel
        }, {
            path: MANAGER_PATHS.PROMOTIONS,
            component: null
        }, {
            path: MANAGER_PATHS.REPORTS,
            component: null
        }
    ]
};