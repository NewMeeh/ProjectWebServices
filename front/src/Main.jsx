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
    var dt;
    useEffect(() => {
        if (!getToken()) {
            navigate("/login");
        }
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', "token":localStorage.getItem('token') },
            body: ""
        };
        fetch('http://localhost:1100/bikes', requestOptions)
            .then(response => response.text())
            .then(data => {
                dt = data;
            });
    });

    return (
            <Layout>
                <Header>
                    <Menu
                        theme="light"
                        mode="horizontal"
                        defaultSelectedKeys={['1']}
                        items={[{label: 'Profile', key: '1'}, {label: 'Panier', key: '2'}]}
                    />
                </Header>
                <Layout>
                    <Content>
                        <Divider orientation="left">Bikes</Divider>
                        <Row gutter={[16, 16]} justify="space-between" align="middle">
                            <BikeList {...props}/>
                        </Row>
                    </Content>
                </Layout>
                <Footer>Footer</Footer>
            </Layout>
        );
}
