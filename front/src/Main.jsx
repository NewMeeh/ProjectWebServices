import { Layout, Menu, Row} from 'antd';
import { Divider } from 'antd';
import {BikeList} from './bikes'
import 'antd/dist/reset.css';
import "./index.css";
import React, {useEffect} from "react";
import {getToken} from './index'
import { useNavigate } from 'react-router-dom';

const { Header, Footer, Content } = Layout;

export const Main = () => {

    const navigate = useNavigate();
    useEffect(() => {
        if (!getToken()) {
            navigate("/login");
        }
    });

    const onClick = (e) => {
        navigate("/"+e.key);
    };

    return (
            <Layout>
                <Header>
                    <Menu
                        onClick={onClick}
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['']}
                        items={[{label: 'Acceuil', key: ''}, {label: 'Profile', key: 'profile'},{label: 'Panier', key: 'Panier'}]}
                    />
                </Header>
                <Layout>
                    <Content>
                        <Divider orientation="left">Bikes</Divider>
                        <Row gutter={[16, 16]} justify="space-between" align="middle">
                            <BikeList />
                        </Row>
                    </Content>
                </Layout>
                <Footer><Divider orientation="right">UGEBikes3000 all rights reserved</Divider></Footer>
            </Layout>
        );
}
