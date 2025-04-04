import React from "react";
import { Field, Formik, Form, ErrorMessage } from "formik";
import { useState } from "react";
import { TextField, Button } from "@mui/material";
import * as Yup from "yup";

const initialValues = { email: "", password: "" };
const validationSchema = Yup.object({
  email: Yup.string().email("Invalid email!").required("Email is required"),
  password: Yup.string()
    .min(6, "Password > 6 char")
    .required("Password is required"),
});
export const Login = () => {
  const [formValue, setFormValue] = useState();

  const handleSubmit = (values) => {
    console.log("Handle Submit", values);
  };

  return (
    <>
      <Formik
        onSubmit={handleSubmit}
        validationSchema={validationSchema}
        initialValues={initialValues}
      >
        <Form className="space-y-5">
          <div className="space-y-5">
            <div>
              <Field
                as={TextField}
                name="email"
                placeholder="Email"
                type="email"
                variant="outlined"
                fullWidth
              />
              <ErrorMessage
                name="email"
                component={"div"}
                className="text-red-500"
              />
            </div>
            <div>
              <Field
                as={TextField}
                name="password"
                placeholder="Password"
                type="password"
                variant="outlined"
                fullWidth
              />
              <ErrorMessage
                name="password"
                component="div"
                className="text-red-500"
              />
            </div>
          </div>
          <Button
            sx={{ padding: ".8rem 0rem" }}
            fullWidth
            type="submit"
            variant="contained"
            color="primary"
          >
            Login
          </Button>
        </Form>
      </Formik>
    </>
  );
};
