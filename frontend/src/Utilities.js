export const currencyFormatted = (num) => {
    return '$' + (Math.round(num * 100) / 100).toFixed(2);
};

export const wordFormatted = (word) => {
    return word.charAt(0).toUpperCase() + word.slice(1);
}

