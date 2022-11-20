import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/street">
        Street
      </MenuItem>
      <MenuItem icon="asterisk" to="/district">
        District
      </MenuItem>
      <MenuItem icon="asterisk" to="/city">
        City
      </MenuItem>
      <MenuItem icon="asterisk" to="/region">
        Region
      </MenuItem>
      <MenuItem icon="asterisk" to="/country">
        Country
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        Location
      </MenuItem>
      <MenuItem icon="asterisk" to="/camera">
        Camera
      </MenuItem>
      <MenuItem icon="asterisk" to="/type-of-camera">
        Type Of Camera
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
