import { Layout, Row} from 'antd';
import { Divider, Modal } from 'antd';
import { Button, Form, Input } from 'antd';
import {BikeList} from './bikes'
import {MenuBar} from './MenuBar'
import 'antd/dist/reset.css';
import "./index.css";
import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';

const { Footer, Content } = Layout;

export function getToken(){
    const token = localStorage.getItem('gtoken');
    if (token != null) {
        return true;
    }
    return false;
}

export const GMain = () => {

    const navigate = useNavigate();
    useEffect(() => {
        if (!getToken()) {
            navigate("/gustave/login");
        }
    });

    return (
            <Layout>
                <MenuBar/>
                <Layout>
                    <Content>
                        <Divider><AddBike></AddBike></Divider>
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

export const AddBike = (props) => {
    const [loading, setLoading] = useState(false);
    const [open, setOpen] = useState(false);
    const showModal = () => {
        setOpen(true);
    };

    const navigate = useNavigate();

    const handleRegisterClick = () => {
        console.log("rclick");
    }
        const handleOk = () => {
            setLoading(true);
            setTimeout(() => {
                setLoading(false);
                setOpen(false);
            }, 3000);
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json', "gtoken": localStorage.getItem('token')},
                body: props.item.bikeId
            };
            fetch('http://localhost:1100/bikes/rent', requestOptions)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                });
        };

        const onFinish = (values: any) => {
            const requestOptions = {
                method: 'PUT',
                headers: {'Content-Type': 'application/json',  "gtoken": localStorage.getItem('token')},
                body: JSON.stringify({name: values.name, locationPrice: values.price, description: values.desc})
            };
            fetch('http://localhost:1100/bikes', requestOptions)
                .then(response => response.text())
                .then(data => {
                    console.log(data)
                });
            setTimeout(() => {
                setLoading(false);
                setOpen(false);
            }, 3000);
            window.location.reload(false);

        };

        const onFinishFailed = (errorInfo: any) => {
            console.log('Failed:', errorInfo);
        };
        const handleCancel = () => {
            setOpen(false);
        };
        return (
            <>
                <Button onClick={showModal}>
                    AddBike
                </Button>
                <Modal
                    open={open}
                    title="Add Bike"
                    onOk={handleOk}
                    onCancel={handleCancel}
                    footer={[
                    ]}
                >
                    <div style={{"margin": "auto", "width": " 50%", "padding": "10px"}}>
                        <Form
                            name="basic"
                            labelCol={{span: 16}}
                            wrapperCol={{span: 16}}
                            initialValues={{remember: true}}
                            onFinish={onFinish}
                            onFinishFailed={onFinishFailed}
                            autoComplete="off"
                        >
                            <Form.Item
                                label="Bike Name"
                                name="name"
                                rules={[{required: true, message: 'Please input your bike name!'}]}
                            >
                                <Input/>
                            </Form.Item>

                            <Form.Item
                                label="Price"
                                name="price"
                                rules={[{required: true, message: 'Please input your Price!'}]}
                            >
                                <Input/>
                            </Form.Item>

                            <Form.Item
                                label="Description"
                                name="desc"
                                rules={[{required: true, message: 'Please input a description!'}]}
                            >
                                <Input/>
                            </Form.Item>

                            <Form.Item wrapperCol={{offset: 8, span: 16}}>
                                <Button type="primary" loading={loading} htmlType="submit">
                                    Add
                                </Button>
                            </Form.Item>
                        </Form>
                    </div>
                </Modal>
            </>
        );
};