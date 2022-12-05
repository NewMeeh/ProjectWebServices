import { Layout,} from 'antd';
import { Divider, Row, Button } from 'antd';
import {BikeList} from './bikes'
import 'antd/dist/reset.css';
import "./index.css";
import React, {useEffect, useState} from "react";
import {getToken} from './Main'
import { useNavigate } from 'react-router-dom';
import MenuBar from './MenuBar'
const {Footer, Content } = Layout;

export const GPanier = () => {

    const [price, setPrice] = useState(false);
    const navigate = useNavigate();
    useEffect(() => {
        setPrice(0);
        if (!getToken()) {
            navigate("/gustave/login");
        }
        const that = this;
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', "gtoken":localStorage.getItem('gtoken') },
            body: {}
        };
        fetch('http://localhost:1090/gbs/myCart/total', requestOptions)
            .then(response => response.text())
            .then(data => {
                setPrice(data);
            });
    },[]);

    const onClick = () => {
        navigate("/gustave/pay");
    };

    return (
        <Layout>
            <MenuBar/>
            <Layout>
                <Content>
                    <Divider orientation="left">Panier</Divider>
                    <div style={{'padding':'10px 20px 10px 20px'}}>
                        <Row gutter={[48, 16]} justify="space-between" align="middle">
                            <BikeList request={"/myCart"} type={2}/>
                        </Row>
                    </div>
                    <Divider orientation="right">Total</Divider>
                    <div style={{'textAlign':'right','padding':'10px 20px 10px 20px'}}>
                        {price}$
                        <Button type="primary" onClick={onClick} key={"pay"}>Pay</Button>
                    </div>
                </Content>
            </Layout>
            <Footer><Divider orientation="right">UGEBikes3000 all rights reserved</Divider></Footer>
        </Layout>
    );
}
