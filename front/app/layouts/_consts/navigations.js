import {MAIN_PATHS, ADMIN_PATHS, MANAGER_PATHS} from '../../_consts/paths';

export const BRAND = {
    link: MAIN_PATHS.HOME,
    desc: 'SajRoz Restaurant'
};

export const MAIN_NAV = {
    navs: [
        {
            link: MAIN_PATHS.HOME,
            desc: 'Strona główna',
            exact: true
        }, {
            link: MAIN_PATHS.MENU,
            desc: 'Menu',
            exact: true
        }, {
            link: MAIN_PATHS.RESERVATIONS,
            desc: 'Rezerwacje',
            exact: false
        }
    ],
    right: [
        {
            link: MAIN_PATHS.PROFILE,
            desc: 'Profil',
            exact: true
        }, {
            link: null,
            desc: 'Wyloguj',
            key: 1,
        }
    ]
};

export const ADMIN_NAV = {
    navs: [
        {
            link: ADMIN_PATHS.HOME,
            desc: 'Panel',
            exact: true
        }, {
            link: ADMIN_PATHS.RESERVATIONS,
            desc: 'Rezerwacje'
        }, {
            link: ADMIN_PATHS.MENU,
            desc: 'Menu'
        }, {
            link: ADMIN_PATHS.TABLES,
            desc: 'Stoliki'
        }, {
            link: ADMIN_PATHS.USERS,
            desc: 'Użytkownicy'
        }
    ],
    right: [
        {
            link: null,
            desc: 'Wyloguj'
        }
    ]
};

export const MANAGER_NAV = {
    navs: [
        {
            link: MANAGER_PATHS.HOME,
            desc: 'Panel',
            exact: true
        }, {
            link: MANAGER_PATHS.PROMOTIONS,
            desc: 'Promocje',
            exact: true
        }, {
            link: MANAGER_PATHS.REPORTS,
            desc: 'Raporty',
            exact: true
        }
    ],
    right: [
        {
            link: null,
            desc: 'Wyloguj',
            key: 1,
        }
    ]
};