import { useEffect, useState, useMemo } from "react";
import { useLocation } from "react-router-dom";
import { FormGroup, FormControl, InputLabel, Input, Button, FormHelperText } from '@mui/material';
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";


const linkStyle = { color: 'inherit', textDecoration: 'none' };

export default function MoveMoney() {
    const location = useLocation();
    const props = location.state;
    const inputsDefault = { type: "", toAccount: "", fromAccount: "", amount: 0 };
    const [inputs, setInputs] = useState(inputsDefault);
    const [transactions, setTransactions] = useState([]);
    let navigate = useNavigate();

    const routeChange = () => {
        let path = `/accounts`;
        navigate(path);
    }

    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({ ...values, [name]: value }))
    }

    const handleDeposit = async (event) => {
        event.preventDefault();
        await fetch(`http://localhost:8080/transaction`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                "toAccount": inputs["toAccount"],
                "amount": inputs["amount"],
                "type": "DEPOSIT"
            })
        })
            .then(res => res.json())
            .then(data => setTransactions([...transactions, data]))
            .catch(err => window.alert(err));
        const formRef = document.querySelector('form');
        formRef.reset();
    }



    const handleWithdrawal = async (event) => {
        event.preventDefault();
        await fetch(`http://localhost:8080/transaction`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                "fromAccount": inputs["fromAccount"],
                "amount": inputs["amount"],
                "type": "WITHDRAWAL"
            })
        })
            .then(res => res.json())
            .then(data => setTransactions([...transactions, data]))
            .catch(err => window.alert(err));
        const formRef = document.querySelector('form');
        formRef.reset();
    }

    const handleTransfer = async (event) => {
            event.preventDefault();
            await fetch(`http://localhost:8080/transaction`, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({
                    "fromAccount": inputs["fromAccount"],
                    "toAccount": inputs["toAccount"],
                    "amount": inputs["amount"],
                    "type": "TRANSFER"
                })
            })
                .then(res => res.json())
                .then(data => setTransactions([...transactions, data]))
                .catch(err => window.alert(err));
            const formRef = document.querySelector('form');
            formRef.reset();
        }

    return (
        <div>

            <h1>Account Management</h1>
            <Button variant="contained" color="success" style={{ marginLeft: 10 }} name="moveMoney" onClick={routeChange}>My Accounts</Button>
            <h3>From here you can deposit, withdraw or transfer funds.</h3>
            <form onSubmit={handleDeposit}>
                <FormGroup sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' }}>
                    <FormControl>
                        <InputLabel htmlFor="toAccount">To Account</InputLabel>
                        <Input id="toAccount" name="toAccount" aria-describedby="to Account help" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <InputLabel htmlFor="amount">Amount $</InputLabel>
                        <Input id="amount" name="amount" aria-describedby="amount help" type="number" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <Button type="submit" variant="outlined">Deposit Funds</Button>
                    </FormControl>
                </FormGroup>
            </form>

            <form onSubmit={handleWithdrawal}>
                <FormGroup sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' }}>
                    <FormControl>
                        <InputLabel htmlFor="fromAccount">From Account</InputLabel>
                        <Input id="fromAccount" name="fromAccount" aria-describedby="from Account help" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <InputLabel htmlFor="amount">Amount $</InputLabel>
                        <Input id="amount" name="amount" aria-describedby="amount help" type="number" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <Button type="submit" variant="outlined">Withdraw Funds</Button>
                    </FormControl>
                </FormGroup>
            </form>


            <form onSubmit={handleTransfer}>
                <FormGroup sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' }}>
                    <FormControl>
                        <InputLabel htmlFor="fromAccount">From Account</InputLabel>
                        <Input id="fromAccount" name="fromAccount" aria-describedby="fromAccount help" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <InputLabel htmlFor="toAccount">To Account</InputLabel>
                        <Input id="toAccount" name="toAccount" aria-describedby="to Account help" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <InputLabel htmlFor="amount">Amount $</InputLabel>
                        <Input id="amount" name="amount" aria-describedby="amount help" type="number" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <Button type="submit" variant="outlined">Transfer Funds</Button>
                    </FormControl>
                </FormGroup>
            </form>
        </div>
    );
}
