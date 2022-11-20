import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './camera.reducer';

export const CameraDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cameraEntity = useAppSelector(state => state.camera.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cameraDetailsHeading">Camera</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cameraEntity.id}</dd>
          <dt>
            <span id="ipAddress">Ip Address</span>
            <UncontrolledTooltip target="ipAddress">IP адрес.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.ipAddress}</dd>
          <dt>
            <span id="port">Port</span>
            <UncontrolledTooltip target="port">Порт.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.port}</dd>
          <dt>
            <span id="login">Login</span>
            <UncontrolledTooltip target="login">Логин от камеры.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.login}</dd>
          <dt>
            <span id="password">Password</span>
            <UncontrolledTooltip target="password">Пароль от камеры.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.password}</dd>
          <dt>
            <span id="mainPath">Main Path</span>
            <UncontrolledTooltip target="mainPath">Основной адрес камеры до IP.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.mainPath}</dd>
          <dt>
            <span id="secondaryPath">Secondary Path</span>
            <UncontrolledTooltip target="secondaryPath">Доп. адрес камеры до IP.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.secondaryPath}</dd>
          <dt>
            <span id="url">Url</span>
            <UncontrolledTooltip target="url">Польный пут до камеры.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.url}</dd>
          <dt>
            <span id="model">Model</span>
            <UncontrolledTooltip target="model">Модель камеры.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.model}</dd>
          <dt>
            <span id="name">Name</span>
            <UncontrolledTooltip target="name">Прикрепленный имя - псевдоним.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.name}</dd>
          <dt>
            <span id="vendorName">Vendor Name</span>
            <UncontrolledTooltip target="vendorName">Бренд камеры.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.vendorName}</dd>
          <dt>
            <span id="activated">Activated</span>
            <UncontrolledTooltip target="activated">Включение/Отключения.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.activated ? 'true' : 'false'}</dd>
          <dt>
            <span id="hlsUrl">Hls Url</span>
            <UncontrolledTooltip target="hlsUrl">Путь до видео файла.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.hlsUrl}</dd>
          <dt>
            <span id="isTemp">Is Temp</span>
            <UncontrolledTooltip target="isTemp">Обозначения временных камер.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.isTemp ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">Status</span>
            <UncontrolledTooltip target="status">Статус камеры.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.status}</dd>
          <dt>
            <span id="info">Info</span>
            <UncontrolledTooltip target="info">Доп инфо.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.info}</dd>
          <dt>Location</dt>
          <dd>{cameraEntity.location ? cameraEntity.location.id : ''}</dd>
          <dt>Type Of Camera</dt>
          <dd>{cameraEntity.typeOfCamera ? cameraEntity.typeOfCamera.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/camera" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/camera/${cameraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CameraDetail;
