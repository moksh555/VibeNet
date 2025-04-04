import { Grid, Card } from "@mui/material";
import React from "react";
import { Login } from "./Login";
import { Register } from "./Register";

const Authentication = () => {
  return (
    <Grid container>
      <Grid className="h-screen overflow-hidden" item xs={7}>
        <img
          className="h-full w-full"
          src="./login-register.jpg"
          alt="login/register"
        />
      </Grid>
      <Grid item xs={5}>
        <div className="px-20 flex flex-col justify-center h-full">
          <Card className="card p-8">
            <div className="flex flex-col items-center mb-5 space-y-1">
              <h1 className="logo text-center">Social Clone</h1>
              <p className="text-center text-sm w-[70%]">
                Connecting you to someone.
              </p>
            </div>
            {/* <Login /> */}
            <Register />
          </Card>
        </div>
      </Grid>
    </Grid>
  );
};

export default Authentication;
