const {DrawASCII, TestASCII} = require("../utils/testUtils");
const {SquareModel} = require("../models/SquareModel");
const {start} = require("../app");

beforeAll(async () => {
  await start()
});

beforeEach(async () => {
  await SquareModel.deleteMany();
})

describe('Placing tiles', () => {
  test('Place one tile', async () => {
    const art =
      "###|" +
      "#1#|" +
      "###|";
    await DrawASCII(art);
    expect(await TestASCII(art)).toBeTruthy();
  })
})

describe('Creating loops', () => {
  test('Simple loop 1', async () => {
    await DrawASCII(
      "111|" +
      "1#1|" +
      "1#1|");
    await DrawASCII(
      "###|" +
      "###|" +
      "#1#|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Simple loop 2', async () => {
    await DrawASCII(
      "111|" +
      "1#1|" +
      "#11|");
    await DrawASCII(
      "###|" +
      "###|" +
      "1##|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Simple loop 3', async () => {
    await DrawASCII(
      "111|" +
      "##1|" +
      "111|");
    await DrawASCII(
      "###|" +
      "1##|" +
      "###|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Simple loop 4', async () => {
    await DrawASCII(
      "#11|" +
      "1#1|" +
      "111|");
    await DrawASCII(
      "1##|" +
      "###|" +
      "###|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Simple loop 5', async () => {
    await DrawASCII(
      "1#1|" +
      "1#1|" +
      "111|");
    await DrawASCII(
      "#1#|" +
      "###|" +
      "###|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Simple loop 6', async () => {
    await DrawASCII(
      "11#|" +
      "1#1|" +
      "111|");
    await DrawASCII(
      "##1|" +
      "###|" +
      "###|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Simple loop 7', async () => {
    await DrawASCII(
      "111|" +
      "1##|" +
      "111|");
    await DrawASCII(
      "###|" +
      "##1|" +
      "###|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Simple loop 8', async () => {
    await DrawASCII(
      "111|" +
      "1#1|" +
      "11#|");
    await DrawASCII(
      "###|" +
      "###|" +
      "##1|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Simple loop multicolor', async () => {
    await DrawASCII(
      "121|" +
      "121|" +
      "111|");
    await DrawASCII(
      "#1#|" +
      "###|" +
      "###|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
  })

  test('Loop over loop', async () => {
    await DrawASCII(
      "111|" +
      "1#1|" +
      "111|");
    expect(await TestASCII(
      "111|" +
      "111|" +
      "111|")).toBeTruthy();
    await DrawASCII(
      "222|" +
      "2#2|" +
      "222|");
    expect(await TestASCII(
      "222|" +
      "222|" +
      "222|")).toBeTruthy();
  })

  test('Larger loop', async () => {
    await DrawASCII(
      "1111|" +
      "1##1|" +
      "1##1|" +
      "1111|");
    expect(await TestASCII(
      "1111|" +
      "1111|" +
      "1111|" +
      "1111|")).toBeTruthy();
  })

  test('Very large loop', async () => {
    await DrawASCII(
      "1111111111|" +
      "1########1|" +
      "1########1|" +
      "1########1|" +
      "1########1|" +
      "1########1|" +
      "1########1|" +
      "1########1|" +
      "1########1|" +
      "1111111111|");
    expect(await TestASCII(
      "1111111111|" +
      "1111111111|" +
      "1111111111|" +
      "1111111111|" +
      "1111111111|" +
      "1111111111|" +
      "1111111111|" +
      "1111111111|" +
      "1111111111|" +
      "1111111111|")).toBeTruthy();
  })

  test('Irregular loop 1', async () => {
    await DrawASCII(
      "111##|" +
      "1#111|" +
      "1###1|" +
      "11111|");
    expect(await TestASCII(
      "111##|" +
      "11111|" +
      "11111|" +
      "11111|")).toBeTruthy();
  })

  test('Irregular loop 1', async () => {
    await DrawASCII(
      "111##|" +
      "1#111|" +
      "1###1|" +
      "11111|");
    expect(await TestASCII(
      "111##|" +
      "11111|" +
      "11111|" +
      "11111|")).toBeTruthy();
  })

  test('Irregular loop 2', async () => {
    await DrawASCII(
      "#333|" +
      "#3#3|" +
      "33#3|" +
      "3##3|" +
      "3333|");
    expect(await TestASCII(
      "#333|" +
      "#333|" +
      "3333|" +
      "3333|" +
      "3333|")).toBeTruthy();
  })
})

describe('Incomplete loops',  () => {
  test('Irregular incomplete loop 1', async () => {
    const art = "11|" +
      "1#|" +
      "11|"
    await DrawASCII(art);
    expect(await TestASCII(art)).toBeTruthy();
  })
})