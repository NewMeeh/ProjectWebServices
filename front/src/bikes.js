import { Card, Col, Button, Modal } from 'antd';
import React, { useState } from 'react';
const { Meta } = Card;

export class Bike extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            item: props.item,
            owner: props.item.ownerName,
            price: props.item.locationPrice,
            rating: props.item.avgGrade,
            name: props.item.bikeName
        };
    }

    render() {
        const title = this.state.owner + "'s " + this.state.name ;
        return (
            <Col span={6}>
            <Card
                hoverable
                style={{width: 240, margin: "auto"}}
                cover={<img alt="Bike" src="https://i0.wp.com/goodcobikeclub.com/wp-content/uploads/2020/07/qi-bin-w4hbafegiac-unsplash.jpg?ssl=1"/>}
            >
                <Meta title={title} description={this.state.rating + " stars"}/>
                <p className="price">{this.state.price + "$"}</p>
                <BikeDesc item={this.state.item}></BikeDesc>
            </Card>
            </Col>
        );
    }
}

export class BikeList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            request: props.request,
            itemList: null,
        }
    }
    
    componentDidMount() {
        const that = this;
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', "token":localStorage.getItem('token') },
        };
        fetch('http://localhost:1100/bikes', requestOptions)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                that.setState({itemList: data});
            });
    }

    createCard (index, item){
        return <Bike key={index} item={item}/>;
    }

    render() {
        const rq = this.state.itemList;
        let itemList=[];
        if (rq != null) {
            rq.forEach((item, index) => {
                itemList.push(this.createCard(index, item))
            })
        }
        return(
            itemList
        );
    }
}

const BikeDesc = (props) => {
    const [loading, setLoading] = useState(false);
    const [open, setOpen] = useState(false);
    const showModal = () => {
        setOpen(true);
    };
    const handleOk = () => {
        setLoading(true);
        setTimeout(() => {
            setLoading(false);
            setOpen(false);
        }, 3000);
    };
    const handleCancel = () => {
        setOpen(false);
    };
    const title = props.item.ownerName + "'s " + props.item.bikeName ;

    return (
        <>
            <Button type="primary" onClick={showModal}>
                More
            </Button>
            <Modal
                open={open}
                title={title}
                onOk={handleOk}
                onCancel={handleCancel}
                footer={[
                    <Button key="back" onClick={handleCancel}>
                        Return
                    </Button>,
                    <Button key="rent" loading={loading} onClick={handleOk}>
                        {props.item.rented == false ? "Rent" : "WaitingList"}
                    </Button>,
                ]}
            >
                <p>{props.item.description}</p>
                <br/>
                <p>Disponible : {props.item.rented == false ? "Oui" : "Non"}</p>
                <p>Prix : {props.item.locationPrice}$</p>
                <p>Note : {props.item.avgGrade} stars ({props.item.grades.length} avis)</p>
            </Modal>
        </>
    );
};
export default BikeDesc;