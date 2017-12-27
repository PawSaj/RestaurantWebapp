import React from 'react';
import {Button} from 'react-bootstrap';
import FieldGroup from '../../_custom/fieldGroup';

const NewDish = () => {
    return (
      <div className="edit-form">
          <FieldGroup
              id="name"
              type="text"
              label="Nazwa"
              placeholder="Wprowadź nazwę"
          />
          <FieldGroup
              id="category"
              label="Kategoria"
              componentClass="select"
              placeholder="Wybierz kategorię">
              <option value="pizza">Pizza</option>
              <option value="soup">Zupa</option>
          </FieldGroup>
          <FieldGroup
              id="ingredients"
              label="Składniki"
              componentClass="textarea"
              placeholder="Uzupełnij składniki"
          />
          <FieldGroup
              id="price"
              type="text"
              label="Cena"
              placeholder="Wprowadź cenę"
          />
          <Button bsClass="btn btn-panel">Zapisz</Button>
      </div>
    );
};

export default NewDish();