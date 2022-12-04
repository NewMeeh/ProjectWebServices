import { Layout, Menu, Row} from 'antd';
import { Divider } from 'antd';
import {BikeList} from './bikes'
import {MenuBar} from './MenuBar'
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
                <MenuBar/>
                <Layout>
                    <Content>
                        <Divider orientation="left">Bikes</Divider>
                        <Row gutter={[16, 16]} justify="space-between" align="middle">
                            <BikeList request={""} type={1}/>
                        </Row>
                    </Content>
                </Layout>
                <Footer><Divider orientation="right">UGEBikes3000 all rights reserved</Divider></Footer>
            </Layout>
        );
}
