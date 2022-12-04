import { Button, Form, Input, Tooltip, DatePicker} from 'antd';
import 'antd/dist/reset.css';
import "./index.css";
import {useEffect} from "react";
import {getToken} from './Main'
import { useNavigate } from 'react-router-dom';

export const GPayment = () => {

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: 'React POST Request Example' })
    };

    const NumericInput = (props) => {
        const {value, onChange} = props;
        const handleChange = (e) => {
            const {value: inputValue} = e.target;
            const reg = /^-?\d*(\.\d*)?$/;
            if (reg.test(inputValue) || inputValue === '' || inputValue === '-') {
                onChange(inputValue);
            }
        };

        // '.' at the end or only '-' in the input box.
        const handleBlur = () => {
            let valueTemp = value;
            if (value.charAt(value.length - 1) === '.' || value === '-') {
                valueTemp = value.slice(0, -1);
            }
            onChange(valueTemp.replace(/0*(\d+)/, '$1'));
        };

        return (
            <Tooltip trigger={['focus']} placement="topLeft" overlayClassName="numeric-input">
                <Input
                    {...props}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    maxLength={16}
                />
            </Tooltip>
        );
    }

    const navigate = useNavigate();
    useEffect(() => {
        if (!getToken()) {
            navigate("/gustave/login");
        }
    });

    const onFinish = (values: any) => {
        //DoTheLoginThing + add token to localStorage
        var j = JSON.stringify({ username: values.username, password: values.password})
        console.log('Success:', j);
    };

    const monthFormat = 'MM/YY';

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    return(
        <div style={{"margin": "auto","width":" 50%","padding": "10px", "border":"1px solid turquoise", "borderRadius":"3px"}}>
            <h2 style={{"textAlign":"center","fontWeight":"900", "color":"#333333"}}>UGEPay</h2>
            <div style={{"margin": "auto","width":" 80%","padding": "10px"}}>
                <h3 style={{"fontWeight":"900", "color":"#333333"}}>Carte de crédit</h3>
                <Form
                    name="basic"
                    labelCol={{ span: 6 }}
                    wrapperCol={{ span: 22 }}
                    initialValues={{ remember: true }}
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    autoComplete="off">

                    <Form.Item
                        label="Numéro de la carte"
                        name="username"
                        rules={[{ required: true, message: 'Please input your cardNumber!' }]}
                    >
                        <NumericInput placeholder="1234 1234 1234 1234"/>
                    </Form.Item>
                    <Input.Group compact>
                        <Form.Item
                            label="Date"
                            name="expDate"
                            rules={[{ required: true, message: 'Please input your expiration date!' }]}
                        >
                            <DatePicker format={monthFormat} picker="month" placeholder="12/22"/>
                        </Form.Item>

                        <Form.Item
                            label="CVC"
                            name="cvc"
                            rules={[{ required: true, message: 'Please input your cvc!' }]}
                        >
                            <NumericInput
                                style={{
                                    width: '40%',
                                }}
                                placeholder="123"
                            />
                        </Form.Item>
                    </Input.Group>

                    <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                        <Button type="primary" htmlType="submit"
                        style={{}}>
                            Pay
                        </Button>
                    </Form.Item>
                </Form>
            </div>

        </div>
    );
}