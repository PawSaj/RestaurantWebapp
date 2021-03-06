import {MAIN_PATHS, RESERVATIONS_PATHS, ADMIN_PATHS, MANAGER_PATHS} from '../paths';

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
import AdminReservations from '../../components/admin/reservations/reservations';
import AdminUsers from '../../components/admin/users/users';
import AdminUser from '../../components/admin/users/userForm';
import AdminDish from '../../components/admin/menu/dishForm';
import AdminSingleReservation from '../../components/admin/reservations/reservationForm';
import AdminTables from '../../components/admin/tables/tables';
import AdminEditTable from '../../components/admin/tables/tableForm';

/* components manager */
import ManagerPanel from '../../components/manager/panel';
import TableFrequency from '../../components/manager/tableFrequency';
import ReservationTraffic from '../../components/manager/reservationTraffic';
import MealOrder from '../../components/manager/mealOrders';
import UserReservations from '../../components/manager/usersReservations'

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
            component: Profile,
            login: true
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
            component: AdminReservations
        }, {
            path: ADMIN_PATHS.MENU,
            component: AdminMenu
        }, {
            path: ADMIN_PATHS.TABLES,
            component: AdminTables
        }, {
            path: ADMIN_PATHS.TABLE,
            component: AdminEditTable
        }, {
            path: ADMIN_PATHS.USERS,
            component: AdminUsers
        }, {
            path: ADMIN_PATHS.USER,
            component: AdminUser
        }, {
            path: ADMIN_PATHS.DISH,
            component: AdminDish
        }, {
            path: ADMIN_PATHS.RESERVATION,
            component: AdminSingleReservation
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
            path: MANAGER_PATHS.TABLE,
            component: TableFrequency
        }, {
            path: MANAGER_PATHS.RESERVATION,
            component: UserReservations
        }, {
            path: MANAGER_PATHS.ORDER,
            component: MealOrder
        }, {
            path: MANAGER_PATHS.TRAFFIC,
            component: ReservationTraffic
        }
    ]
};
