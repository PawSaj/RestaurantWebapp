import moment from 'moment';

export default [
    {
        'title': 'All Day Event very long title',
        'allDay': true,
        'start': moment().toDate(),
        'end': moment().add(4, "hours").toDate()
    },
    {
        'title': 'Conference',
        'start': moment().toDate(),
        'end': moment().add(4, "hours").toDate(),
        desc: 'Big conference for important people'
    }
]