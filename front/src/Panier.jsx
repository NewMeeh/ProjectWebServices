import { Layout, Menu, Row} from 'antd';
import { Divider } from 'antd';
import {BikeList} from './bikes'
import 'antd/dist/reset.css';
import "./index.css";
import React, {useState, useEffect} from "react";
import {getToken} from './index'
import { useNavigate } from 'react-router-dom';
import MenuBar from './MenuBar'
const { Header, Footer, Content } = Layout;

export const Panier = () => {

    const [usr, setUsr] = useState(0);

    const navigate = useNavigate();
    useEffect(() => {
        if (!getToken()) {
            navigate("/login");
        }
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin":"*" },
            body: JSON.stringify({ username: ".", password: "."})
        };
        fetch('http://localhost:1080/userInfo', requestOptions)
            .then(response => response.text())
            .then(data => {

            });
    });

    const onClick = (e) => {
        navigate("/"+e.key);
    };

    return (
        <Layout>
            <MenuBar/>
            <Layout>
                <Content>
                    <Divider orientation="left">Panier</Divider>
                    <div style={{'padding':'10px 20px 10px 20px'}}>
                        <p>Salut c'est Ninho</p>
                    </div>
                    <Divider orientation="right">Total</Divider>
                    <div style={{'textAlign':'right','padding':'10px 20px 10px 20px'}}>
                        xxx$
                    </div>
                </Content>
            </Layout>
            <Footer><Divider orientation="right">UGEBikes3000 all rights reserved</Divider></Footer>
        </Layout>
    );
}
