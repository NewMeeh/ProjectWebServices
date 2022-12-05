import { Layout, Menu} from 'antd';
import 'antd/dist/reset.css';
import "./index.css";
import React from "react";
import { useNavigate } from 'react-router-dom';

const { Header } = Layout;
export const MenuBar = () => {

    const navigate = useNavigate();
    const onClick = (e) => {
        navigate(""+e.key);
    };

    return(
    <Header>
        <div className="logo">UGEBikes3000</div>
        <Menu
            onClick={onClick}
            theme="dark"
            mode="horizontal"
            items={[{label: 'Accueil', key: '/gustave/'}, {label: 'Profile', key: '/gustave/profile'},{label: 'Panier', key: '/gustave/Panier'},{label: 'Internes', key: '/eiffel'}]}
        />
    </Header>
    );
}
export default MenuBar;