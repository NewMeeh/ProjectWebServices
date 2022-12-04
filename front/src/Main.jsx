import { Layout, Row} from 'antd';
import { Divider, Modal } from 'antd';
import { Button, Form, Input } from 'antd';
import 'antd/dist/reset.css';
import "./index.css";
import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';

const { Footer, Content } = Layout;

export const Main = () => {
    const navigate = useNavigate();

    const buyClick = () => {navigate("/gustave/");};
    const rentClick = () => {navigate("/eiffel/");};

    return (
        <Layout>
            <Layout>
                <Content>
                    <Divider>Welcome</Divider>
                    <Button onClick={buyClick}>Buy</Button>
                    <Button onClick={rentClick}>Rent</Button>
                </Content>
            </Layout>
            <Footer><Divider orientation="right">UGEBikes3000 all rights reserved</Divider></Footer>
        </Layout>
);
}
