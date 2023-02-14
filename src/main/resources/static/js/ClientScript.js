const paymentStartClient = () => {
    console.log("Payment Started.....");
    const amount = 499;
    console.log(amount);

    registerClient($("#name").val(), $("#username").val(), $("#password").val(), $("#email").val());

    // we will use ajax to send request to server to create order -> jQuery
    $.ajax(
        {
            url: '/create_order',
            data: JSON.stringify({amount: amount, info: 'order_request'}),
            contentType: 'application/json',
            type: 'POST',
            dataType: 'json',

            success: function (response) {
                // invoked where success
                console.log(response)
                if (response.status == "created") {
                    // open payment form
                    let options = {
                        key: 'rzp_test_KJlRFeNbThTgLB',
                        amount: response.amount,
                        currency: 'INR',
                        name: 'Fitxplore',
                        description: 'Trainers Subscription Payment',
                        image: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQMAAADCCAMAAAB6zFdcAAAAk1BMVEX///+Ii46ChYiAg4f/dhP3+Pj8/PyFiIvP0NGkp6mztbbBw8Tf4OGho6X/bgD/agDIycr/cgC8vb/y8/P/5tnW19j/7+b//Pnr7Oz/pHL/6+CRlJf/1sGur7H/r4Pf3+D/nGP/gCr/vp2WmZv/0bj/x6r/jEb/hTf/4M//qXr/fCH/gjH/to//kE//lVf/zLL/waEIV0qCAAADyUlEQVR4nO3Y23abOABGYYzs4AMGY8eHlBBPmjTtpGk77/90IyEDMkhAGbw6F/u7SmSBpB8hBJ4HAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP9fp/l8NuzIh/j5U6Pw/jn6XPw9exEWwXpfVd+KdNfZ0HkxqZ8k3RqHWduZZKFxiru1rSu5lztvvjnPB0XwGE+T+LVW+CVOpvHh8k/mT2yCatwnMfGzroYyETRP4otNVcHaTiCMM6+EtSv5mbbefjswg9d4Op3GT1dlD3lZMTscGcjuBZepd/YnwbqjnaWj+2LVnoEcXXXqrasrOoP56jQoA+8tkgOO3o5lwfFrdBXLrjGF9c0gQ1joGmF3BieRX/UaNSRR9NvaTl6jvLqz1HkvpPJ63A1LQHpSVz1J7i//fosSFcEXo8bM4rSWIYhd3wwyFVlYP8leDjGo5rqlmTCVIaTtXckNHr72Ghuj/nydSAvZO6HXxR4ZyAj8fbNYhTBpPXAnyqhv6vCez/4H+edfce3OcJOX1tcrWo8M1EgsU3XmKDekxu1yU3roX+UjUYXxd69jfjsDS/HM78xALQhha42x6FtAJTCNf/U6QnXf730vDM5A3S3VvRAu7cYJ6ZtaE3KNpUDuv5pW6ml/6f5IGdjaWapVs1wTTyKwG+luOeSTYBo1loJQ+Db6maxrjJKBtR2VtF9e5Y1rfyAs6+3v+/SuZ0Hy/VD7ZWnZ311vXsbJwNlO9fRUGQTWKzLGk0M/H6N8UXi8/invW3P6+WJZ1Bgng8zejrlJ2qiGVk2bMSLQ+6T3x/d8p/Bx9ZvK4GXRYOxLx8ug2c46n/6X6iqD7fBRtjn+yPfLP47FXz/NRUFm4Le/i4yWgW0bFYpqg3C7DO6nSfWC8KFnhPE6/Ycz8NZV+zfL4JfeGhTvz4/qfSGJqk3CKBn45vJ+dWQ50V0ZZLfP4Kl+4Q+1zWLfDO5siioLtbad6r+e0url05nBop5Ba0MDHN+aC0DtpaFXBvIyWwVnXeUsbFWEuRHul4Hj2einw3dJ+R659iAoSv/R/3RncHZ/2giCYiSOOtVnoszRTi0DV0NdH3HcPqLmhsBTO+ekzGArjK8YVqH7E1eZgbewfUsLxKI8i9wWW7d7WVU+b2loeAbH57ixMVQOb3Fxf8xS0fmhzD4/1b1QjWq/bkzjqw+zu9QIxLOXr+37dmnyX94YbAkoxgrR/ZHGukxJtSM7Pv642jHKb/UdCQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA/Fn/AsAdOS2kJEwsAAAAAElFTkSuQmCC",
                        order_id: response.id,
                        handler: function (response) {
                            console.log(response.razorpay_payment_id);
                            console.log(response.razorpay_order_id);
                            console.log(response.razorpay_signature);
                            console.log('Payment Successful !!');
                            updatePaymentOnServer(response.razorpay_payment_id, response.razorpay_order_id, 'paid');
                        },
                        prefill: {
                            name: "",
                            email: "",
                            contact: "",
                        },
                        notes: {
                            address: "Fitxplore"
                        },
                        theme: {
                            color: "#3399cc"
                        }
                    };
                    let rzp = new Razorpay(options);

                    rzp.on('payment.failed', function (response) {
                        console.log(response.error.code);
                        console.log(response.error.description);
                        console.log(response.error.source);
                        console.log(response.error.step);
                        console.log(response.error.reason);
                        console.log(response.error.metadata.order_id);
                        console.log(response.error.metadata.payment_id);
                        alert("Oops Payment failed !!")
                        swal("Failed !!", "Oops Payment failed !!", "error");
                    });

                    rzp.open();
                }
            },
            error: function (error) {
                // invoked when error
                console.log(error)
                alert("something went wrong !!")
            }
        }
    )
};

function registerClient(name, username, password, email) {
    $.ajax({
            url: "/clientRegister",
            data: JSON.stringify({name: name, username: username, password: password, email: email}),
            contentType: 'application/json',
            type: 'POST',
            dataType: 'json',
        }
    );
}

function updateDataClient(username, password) {
    $.ajax(
        {
            url: "/update_entries_client",
            data: JSON.stringify({username: username, password: password}),
            contentType: 'application/json',
            type: 'POST',
            dataType: 'json',
        }
    );
}

function updatePaymentOnServer(payment_id, order_id, id_status) {
    $.ajax(
        {
            url: "/update_order",
            data: JSON.stringify({payment_id: payment_id, order_id: order_id, id_status: id_status}),
            contentType: 'application/json',
            type: 'POST',
            dataType: 'json',
            success: function (response) {
                updateDataClient($("#username").val(), $("#password"));
                swal("Good job!", "Congrats !! Payment Successful", "success");
            },
            error: function (error) {
                swal("Failed !!", "Payment Successfull ...Currently server down we will contact you !!", "error");
            },
        }
    );
}