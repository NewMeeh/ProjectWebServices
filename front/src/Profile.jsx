import { Layout, Menu, Row} from 'antd';
import { Divider } from 'antd';
import {BikeList} from './bikes'
import 'antd/dist/reset.css';
import "./index.css";
import React, {useState, useEffect} from "react";
import {getToken} from './index'
import { useNavigate } from 'react-router-dom';

const { Header, Footer, Content } = Layout;

export const Profile = () => {

    const [usr, setUsr] = useState(0);

    const navigate = useNavigate();
    useEffect(() => {
        if (!getToken()) {
            navigate("/login");
        }
        /*
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin":"*" },
            body: JSON.stringify({ username: ".", password: "."})
        };
        fetch('http://localhost:1080/userInfo', requestOptions)
            .then(response => response.text())
            .then(data => {
                if (data.localeCompare("") != 0) {
                    localStorage.setItem('token', data)
                    navigate("/", { replace: true });
                }
            });
         */
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
                    defaultSelectedKeys={['profile']}
                    items={[{label: 'Acceuil', key: ''}, {label: 'Profile', key: 'profile'},{label: 'Panier', key: 'Panier'}]}
                />
            </Header>
            <Layout>
                <Content>
                    <Divider orientation="left">Info</Divider>
                    <div style={{'padding':'10px 20px 10px 20px'}}>
                        <p>Salut c'est Ninho</p>
                    </div>
                    <Divider orientation="left">My Bikes</Divider>
                    <Row gutter={[16, 16]} justify="space-between" align="middle">
                        <BikeList />
                    </Row>
                    <Divider orientation="left">Rented Bikes</Divider>
                    <Row gutter={[16, 16]} justify="space-between" align="middle">
                        <BikeList />
                    </Row>
                </Content>
            </Layout>
            <Footer><Divider orientation="right">UGEBikes3000 all rights reserved</Divider></Footer>
        </Layout>
    );
}
