# Currency Converter Application

A Spring Boot web application that allows users to convert amounts between different currencies using the [CurrencyBeacon API](https://www.currencybeacon.com/).

This application is built with **Spring Boot**, **Thymeleaf**, **WebClient**, and **MySQL** for caching conversion rates.

---

## Features

- Fetches a list of currencies from CurrencyBeacon API.
- Converts a specified amount from one currency to another.
- Caches conversion rates for faster performance (1-hour cache).
- Form validation for required fields and amount restrictions (whole numbers only).
- Displays conversion results dynamically on the same page.

---

## Technologies Used

- Java 17+
- Spring Boot 3.x
- Thymeleaf for server-side rendering
- Spring WebFlux `WebClient` for API calls
- Lombok for reducing boilerplate code
- Spring Data JPA for caching conversion rates
- MySQL(configurable) as the database

---

## Getting Started

### Prerequisites

- Java 17
- Maven 3.x
- Internet access (to call CurrencyBeacon API)
