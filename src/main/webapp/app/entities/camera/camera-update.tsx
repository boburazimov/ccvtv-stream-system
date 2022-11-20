import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { ITypeOfCamera } from 'app/shared/model/type-of-camera.model';
import { getEntities as getTypeOfCameras } from 'app/entities/type-of-camera/type-of-camera.reducer';
import { ICamera } from 'app/shared/model/camera.model';
import { CameraStatusEnum } from 'app/shared/model/enumerations/camera-status-enum.model';
import { getEntity, updateEntity, createEntity, reset } from './camera.reducer';

export const CameraUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locations = useAppSelector(state => state.location.entities);
  const typeOfCameras = useAppSelector(state => state.typeOfCamera.entities);
  const cameraEntity = useAppSelector(state => state.camera.entity);
  const loading = useAppSelector(state => state.camera.loading);
  const updating = useAppSelector(state => state.camera.updating);
  const updateSuccess = useAppSelector(state => state.camera.updateSuccess);
  const cameraStatusEnumValues = Object.keys(CameraStatusEnum);

  const handleClose = () => {
    navigate('/camera' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocations({}));
    dispatch(getTypeOfCameras({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cameraEntity,
      ...values,
      location: locations.find(it => it.id.toString() === values.location.toString()),
      typeOfCamera: typeOfCameras.find(it => it.id.toString() === values.typeOfCamera.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          status: 'ONLINE',
          ...cameraEntity,
          location: cameraEntity?.location?.id,
          typeOfCamera: cameraEntity?.typeOfCamera?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ccvtvApp.camera.home.createOrEditLabel" data-cy="CameraCreateUpdateHeading">
            Create or edit a Camera
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="camera-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Ip Address"
                id="camera-ipAddress"
                name="ipAddress"
                data-cy="ipAddress"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 64, message: 'This field cannot be longer than 64 characters.' },
                }}
              />
              <UncontrolledTooltip target="ipAddressLabel">IP адрес.</UncontrolledTooltip>
              <ValidatedField
                label="Port"
                id="camera-port"
                name="port"
                data-cy="port"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 4, message: 'This field cannot be longer than 4 characters.' },
                }}
              />
              <UncontrolledTooltip target="portLabel">Порт.</UncontrolledTooltip>
              <ValidatedField
                label="Login"
                id="camera-login"
                name="login"
                data-cy="login"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 64, message: 'This field cannot be longer than 64 characters.' },
                }}
              />
              <UncontrolledTooltip target="loginLabel">Логин от камеры.</UncontrolledTooltip>
              <ValidatedField
                label="Password"
                id="camera-password"
                name="password"
                data-cy="password"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 128, message: 'This field cannot be longer than 128 characters.' },
                }}
              />
              <UncontrolledTooltip target="passwordLabel">Пароль от камеры.</UncontrolledTooltip>
              <ValidatedField
                label="Main Path"
                id="camera-mainPath"
                name="mainPath"
                data-cy="mainPath"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 128, message: 'This field cannot be longer than 128 characters.' },
                }}
              />
              <UncontrolledTooltip target="mainPathLabel">Основной адрес камеры до IP.</UncontrolledTooltip>
              <ValidatedField
                label="Secondary Path"
                id="camera-secondaryPath"
                name="secondaryPath"
                data-cy="secondaryPath"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 128, message: 'This field cannot be longer than 128 characters.' },
                }}
              />
              <UncontrolledTooltip target="secondaryPathLabel">Доп. адрес камеры до IP.</UncontrolledTooltip>
              <ValidatedField
                label="Url"
                id="camera-url"
                name="url"
                data-cy="url"
                type="text"
                validate={{
                  maxLength: { value: 256, message: 'This field cannot be longer than 256 characters.' },
                }}
              />
              <UncontrolledTooltip target="urlLabel">Польный пут до камеры.</UncontrolledTooltip>
              <ValidatedField
                label="Model"
                id="camera-model"
                name="model"
                data-cy="model"
                type="text"
                validate={{
                  maxLength: { value: 64, message: 'This field cannot be longer than 64 characters.' },
                }}
              />
              <UncontrolledTooltip target="modelLabel">Модель камеры.</UncontrolledTooltip>
              <ValidatedField label="Name" id="camera-name" name="name" data-cy="name" type="text" />
              <UncontrolledTooltip target="nameLabel">Прикрепленный имя - псевдоним.</UncontrolledTooltip>
              <ValidatedField
                label="Vendor Name"
                id="camera-vendorName"
                name="vendorName"
                data-cy="vendorName"
                type="text"
                validate={{
                  maxLength: { value: 64, message: 'This field cannot be longer than 64 characters.' },
                }}
              />
              <UncontrolledTooltip target="vendorNameLabel">Бренд камеры.</UncontrolledTooltip>
              <ValidatedField label="Activated" id="camera-activated" name="activated" data-cy="activated" check type="checkbox" />
              <UncontrolledTooltip target="activatedLabel">Включение/Отключения.</UncontrolledTooltip>
              <ValidatedField label="Hls Url" id="camera-hlsUrl" name="hlsUrl" data-cy="hlsUrl" type="text" />
              <UncontrolledTooltip target="hlsUrlLabel">Путь до видео файла.</UncontrolledTooltip>
              <ValidatedField label="Is Temp" id="camera-isTemp" name="isTemp" data-cy="isTemp" check type="checkbox" />
              <UncontrolledTooltip target="isTempLabel">Обозначения временных камер.</UncontrolledTooltip>
              <ValidatedField label="Status" id="camera-status" name="status" data-cy="status" type="select">
                {cameraStatusEnumValues.map(cameraStatusEnum => (
                  <option value={cameraStatusEnum} key={cameraStatusEnum}>
                    {cameraStatusEnum}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="statusLabel">Статус камеры.</UncontrolledTooltip>
              <ValidatedField label="Info" id="camera-info" name="info" data-cy="info" type="text" />
              <UncontrolledTooltip target="infoLabel">Доп инфо.</UncontrolledTooltip>
              <ValidatedField id="camera-location" name="location" data-cy="location" label="Location" type="select">
                <option value="" key="0" />
                {locations
                  ? locations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="camera-typeOfCamera" name="typeOfCamera" data-cy="typeOfCamera" label="Type Of Camera" type="select">
                <option value="" key="0" />
                {typeOfCameras
                  ? typeOfCameras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/camera" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CameraUpdate;
