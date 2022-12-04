import { Card, Col } from 'antd';
import React from 'react';
const { Meta } = Card;

export class Bike extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            owner: props.owner,
            price: props.price,
            rating: props.rating,
            name: props.bikeName
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
            </Card>
            </Col>
        );
    }
}

export class BikeList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
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
        return <Bike key={index} owner={item.ownerName} price={item.locationPrice} rating={item.avgGrade} owned={item.rented} bikeName={item.bikeName}/>;
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