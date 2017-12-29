export const serializeFrom = ({formId}) => {
    let formElements = document.getElementById(formId).elements;
    let serializedObj = {};
    console.log('form: ', formElements)

    for (let element of formElements) {
        if (element.tagName === 'INPUT' && element.id && !element.id.includes('Confirm')) {
            serializedObj[element.id] = element.value;
        }
    }

    return serializedObj;
};