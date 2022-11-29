import { createRoot } from "react-dom/client";
import { Layout, Menu, Row, Col } from 'antd';
import { Divider } from 'antd';
import {BikeList} from './bikes'
import 'antd/dist/reset.css';
import "./index.css";

const { Header, Footer, Content } = Layout;

const App = () => {
    return (
        <Layout>
            <Header>
                <Menu
                    theme="light"
                    mode="horizontal"
                    defaultSelectedKeys={['1']}
                    items={new Array(3).fill(null).map((_, index) => {
                        const key = index + 1;
                        return {
                            key,
                            label: `nav ${key}`,
                        };
                    })}
                />
            </Header>
            <Layout>
                <Content>
                    <Divider orientation="left">Bikes</Divider>
                    <Row gutter={[16, 16]} justify="space-between" align="middle">
                        <BikeList/>
                    </Row>
                </Content>
            </Layout>
            <Footer>Footer</Footer>
        </Layout>
    );
};

const root = createRoot(document.getElementById("root"));
root.render(<App />);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
//reportWebVitals();
