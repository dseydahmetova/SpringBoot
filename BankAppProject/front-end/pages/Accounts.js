import { useState, useMemo } from "react";
import { useLocation } from "react-router-dom";
import {FormGroup, FormControl, InputLabel, Input, Button } from '@mui/material';
import BasicTable from "../componets/BasicTable";

export default function Accounts() {
    const location = useLocation();
    const props = location.state;
    const inputsDefault = {accountName: "", openingBalance: 0};
    const [inputs, setInputs] = useState(inputsDefault);
    const [accounts, setAccounts] = useState([]);

   useMemo(() => {
    fetch(`http://localhost:8080/customer/${props.id}`)
    .then(res =>{
                if(res.ok){
                    return res.json();
                } else {
                    throw new Error('Something went wrong');
                }
            })
    .then(data => data.accounts.map(account => {
        fetch(`http://localhost:8080/account/${account}`)
        .then(res =>{
                if(res.ok){
                    return res.json();
                } else {
                    throw new Error('Something went wrong');
                }
            })
        .then(data => setAccounts(accounts => [...accounts, data]))
    }))
    }, []);

    
  const handleDelete = (event) => {
    event.preventDefault();
    const accountNumber = event.target.value;
    fetch(`http://localhost:8080/account/${accountNumber}`, {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
        }
    })
    .then(res =>{
        if(res.ok){
            return res.json();
        } else {
            throw new Error('Something went wrong');
        }
    })
    .then(data => window.alert(`Account deleted successfully, cash on hand: $${data}`))
    .then(setAccounts(accounts.filter(account => account.number != accountNumber)))
    .catch(err => window.alert(err));
    }
   
    
    const handlesubmit = async (event) => {
        event.preventDefault();
        await fetch(`http://localhost:8080/account`,{
            method: 'POST', 
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                "customerId": props.id,
                "accountName": inputs["accountName"],
                "openingBalance": inputs["openingBalance"]
            })
        })
        .then(res =>{
                if(res.ok){
                    return res.json();
                } else {
                    throw new Error('Something went wrong');
                }
        })
        .then(data => setAccounts([...accounts, data]))
        .catch(err => window.alert(err));
        const formRef = document.querySelector('form');
        formRef.reset();
    }
    
    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({...values, [name]: value}))
    }

    return (
        <div>
            <h1>Welcome {props.fullName} </h1>
            <h3>Here are your accounts: </h3>
            <div>{accounts.length > 0 && <BasicTable accounts={accounts} handleDelete={handleDelete}/>}</div>
            <div>
                <form onSubmit={handlesubmit} style={{marginTop: '3rem'}}>
                    <FormGroup sx={{display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between'}}>
                        <FormControl>
                            <InputLabel htmlFor="accountName">Account Name</InputLabel>
                            <Input id="accountName" name="accountName" aria-describedby="account name help" onChange={handleChange} />
                        </FormControl>
                        <FormControl>
                            <InputLabel htmlFor="openingBalance">Opening Balance</InputLabel>
                            <Input id="openingBalance" name="openingBalance" aria-describedby="opening balance help" type="number" onChange={handleChange} />
                        </FormControl>
                        <FormControl>
                            <Button type="submit">Submit</Button>
                        </FormControl>
                    </FormGroup>
                </form>
            </div>
        </div>
    );
}